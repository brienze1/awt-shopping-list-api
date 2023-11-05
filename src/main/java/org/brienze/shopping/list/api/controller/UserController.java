package org.brienze.shopping.list.api.controller;

import org.brienze.shopping.list.api.model.CreateUserRequest;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.model.UserAuthorization;
import org.brienze.shopping.list.api.model.UserCredential;
import org.brienze.shopping.list.api.usecase.AuthorizeUserUseCase;
import org.brienze.shopping.list.api.usecase.CreateUserUseCase;
import org.brienze.shopping.list.api.usecase.GetUserCredentialUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserCredentialUseCase getUserCredentialUseCase;
    private final AuthorizeUserUseCase authorizeUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserCredentialUseCase getUserCredentialUseCase,
                          AuthorizeUserUseCase authorizeUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserCredentialUseCase = getUserCredentialUseCase;
        this.authorizeUserUseCase = authorizeUserUseCase;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(createUserUseCase.create(createUserRequest));
    }

    @PostMapping(path = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String authorization) {
        UserCredential userCredential = getUserCredentialUseCase.getByBasicAuthorization(authorization);

        UserAuthorization userAuthorization = authorizeUserUseCase.authorize(authorization, userCredential);

        return ResponseEntity.ok(userAuthorization);
    }

}
