package org.brienze.shopping.list.api.persistence;

import org.brienze.shopping.list.api.exception.UsernameTakenException;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistence {

    private final UserRepository userRepository;

    public UserPersistence(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if(ex.getMessage().contains("Unique index or primary key violation")) {
                throw new UsernameTakenException(ex);
            }
            throw ex;
        }
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserCredentialUsername(username);
    }
}
