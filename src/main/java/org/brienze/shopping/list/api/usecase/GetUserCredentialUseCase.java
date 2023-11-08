package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.exception.UnauthorizedUserException;
import org.brienze.shopping.list.api.model.UserCredential;
import org.brienze.shopping.list.api.persistence.UserCredentialPersistence;
import org.brienze.shopping.list.api.utils.PasswordUtils;
import org.springframework.stereotype.Component;

@Component
public class GetUserCredentialUseCase {

    private final UserCredentialPersistence userCredentialPersistence;

    public GetUserCredentialUseCase(UserCredentialPersistence userCredentialPersistence) {
        this.userCredentialPersistence = userCredentialPersistence;
    }

    public UserCredential getByBasicAuthorization(String authorization) {
        String username = PasswordUtils.decodeUsername(authorization);

        return userCredentialPersistence.findByUsername(username).orElseThrow(UnauthorizedUserException::new);
    }

    public UserCredential getByUsername(String username) {
        return userCredentialPersistence.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
