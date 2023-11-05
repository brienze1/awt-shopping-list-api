package org.brienze.shopping.list.api.handler;

import org.brienze.shopping.list.api.exception.ShoppingListException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ShoppingListException.class})
    public ResponseEntity<Object> transferTypeExceptionHandler(ShoppingListException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getErrorResponse());
    }

}
