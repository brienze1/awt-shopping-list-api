package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.model.CreateUserRequest;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.persistence.UserPersistence;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase {

    private final UserPersistence userPersistence;

    public CreateUserUseCase(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    public User create(CreateUserRequest createUserRequest) {
        User user = new User(createUserRequest);

        return userPersistence.create(user);
    }

}
