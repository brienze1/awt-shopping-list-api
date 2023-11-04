package org.brienze.shopping.list.api.persistence;

import org.brienze.shopping.list.api.model.UserCredential;
import org.brienze.shopping.list.api.repository.UserCredentialRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCredentialPersistence {

    private final UserCredentialRepository userCredentialRepository;


    public UserCredentialPersistence(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    public Optional<UserCredential> findByUsername(String username) {
        return userCredentialRepository.findByUsername(username);
    }
}
