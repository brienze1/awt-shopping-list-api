package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class UserNotFound extends ShoppingListException {
    public UserNotFound() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
