package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.exception.UserNotFound;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.persistence.UserPersistence;
import org.brienze.shopping.list.api.utils.JwtUtils;
import org.springframework.stereotype.Component;

@Component
public class GetUserUseCase {

    private final UserPersistence userPersistence;

    public GetUserUseCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User findByToken(String token) {
        String username = JwtUtils.decodeUsername(token);

        return userPersistence.findByUsername(username).orElseThrow(UserNotFound::new);
    }

}
