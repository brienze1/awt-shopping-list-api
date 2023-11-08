package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidToken extends ShoppingListException {
    public InvalidToken() {
        super(HttpStatus.FORBIDDEN, "Token is invalid");
    }

    public InvalidToken(Throwable cause) {
        super(cause, HttpStatus.FORBIDDEN, "Token is invalid");
    }
}
