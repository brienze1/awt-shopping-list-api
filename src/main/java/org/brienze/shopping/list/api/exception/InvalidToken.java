package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidToken extends ShoppingListException {
    public InvalidToken() {
        super(HttpStatus.UNAUTHORIZED, "Token is invalid");
    }

    public InvalidToken(Throwable cause) {
        super(cause, HttpStatus.UNAUTHORIZED, "Token is invalid");
    }
}
