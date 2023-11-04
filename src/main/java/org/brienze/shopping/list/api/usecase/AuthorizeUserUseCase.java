package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.model.UserAuthorization;
import org.brienze.shopping.list.api.model.UserCredential;
import org.brienze.shopping.list.api.persistence.UserAuthorizationPersistence;
import org.brienze.shopping.list.api.utils.PasswordUtils;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeUserUseCase {

    private final UserAuthorizationPersistence userAuthorizationPersistence;

    public AuthorizeUserUseCase(UserAuthorizationPersistence userAuthorizationPersistence) {
        this.userAuthorizationPersistence = userAuthorizationPersistence;
    }

    public UserAuthorization authorize(String authorization, UserCredential userCredential) {
        String hashedPassword = PasswordUtils.hash(authorization, userCredential.salt());

        if (!userCredential.allows(hashedPassword)) {
            throw new RuntimeException("Wrong password");
        }

        return userAuthorizationPersistence.save(userCredential.authorization());
    }
}
