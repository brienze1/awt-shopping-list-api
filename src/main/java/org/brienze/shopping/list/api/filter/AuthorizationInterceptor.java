package org.brienze.shopping.list.api.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.brienze.shopping.list.api.annotation.Authorized;
import org.brienze.shopping.list.api.usecase.AuthorizeUserUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final AuthorizeUserUseCase authorizeUserUseCase;

    public AuthorizationInterceptor(AuthorizeUserUseCase authorizeUserUseCase) {
        this.authorizeUserUseCase = authorizeUserUseCase;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Authorized annotation = ((HandlerMethod) handler).getMethod().getAnnotation(Authorized.class);
            if (annotation != null) {
                authorizeUserUseCase.authorize(request.getHeader("Authorization"));
            }
        }
        return true;
    }
}
