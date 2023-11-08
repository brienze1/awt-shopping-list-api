package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class UsernameTakenException extends ShoppingListException{
    public UsernameTakenException(Throwable cause) {
        super(cause, HttpStatus.CONFLICT, "Username not available");
    }
}
