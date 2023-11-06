package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

public class ShoppingListNotFound extends ShoppingListException {
    public ShoppingListNotFound() {
        super(HttpStatus.NOT_FOUND, "Shopping list not found");
    }
}
