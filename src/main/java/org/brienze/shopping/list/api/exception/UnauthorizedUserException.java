package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends ShoppingListException {
    public UnauthorizedUserException() {
        super(HttpStatus.FORBIDDEN, "User not authorized");
    }
}
