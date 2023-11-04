package org.brienze.shopping.list.api.persistence;

import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserPersistence {

    private final UserRepository userRepository;

    public UserPersistence(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

}
