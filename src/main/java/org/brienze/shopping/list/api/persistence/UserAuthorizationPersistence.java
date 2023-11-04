package org.brienze.shopping.list.api.persistence;

import org.brienze.shopping.list.api.model.UserAuthorization;
import org.brienze.shopping.list.api.repository.UserAuthorizationRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationPersistence {

    private final UserAuthorizationRepository userAuthorizationRepository;

    public UserAuthorizationPersistence(UserAuthorizationRepository userAuthorizationRepository) {
        this.userAuthorizationRepository = userAuthorizationRepository;
    }

    public UserAuthorization save(UserAuthorization userAuthorization) {
        return this.userAuthorizationRepository.save(userAuthorization);
    }
}
