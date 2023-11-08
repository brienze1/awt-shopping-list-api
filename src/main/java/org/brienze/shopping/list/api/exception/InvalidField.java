package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class InvalidField extends ShoppingListException {
    public InvalidField(String description) {
        super(HttpStatus.BAD_REQUEST, description);
    }
}
