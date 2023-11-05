package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.model.UserAuthorization;
import org.brienze.shopping.list.api.model.UserCredential;
import org.brienze.shopping.list.api.persistence.UserAuthorizationPersistence;
import org.brienze.shopping.list.api.utils.JwtUtils;
import org.brienze.shopping.list.api.utils.PasswordUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeUserUseCase {

    private final UserAuthorizationPersistence userAuthorizationPersistence;
    private final GetUserCredentialUseCase getUserCredentialUseCase;

    public AuthorizeUserUseCase(UserAuthorizationPersistence userAuthorizationPersistence, GetUserCredentialUseCase getUserCredentialUseCase) {
        this.userAuthorizationPersistence = userAuthorizationPersistence;
        this.getUserCredentialUseCase = getUserCredentialUseCase;
    }

    public UserAuthorization authorize(String authorization, UserCredential userCredential) {
        String hashedPassword = PasswordUtils.hash(authorization, userCredential.salt());

        if (!userCredential.allows(hashedPassword)) {
            throw new RuntimeException("Wrong password");
        }

        return userAuthorizationPersistence.save(userCredential.authorization());
    }

    public void authorize(String token) {
        String username = JwtUtils.decodeUsername(token);

        UserCredential userCredential = getUserCredentialUseCase.getByUsername(username);

        userCredential.validate(token);
    }
}
