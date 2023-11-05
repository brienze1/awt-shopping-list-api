package org.brienze.shopping.list.api.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

public abstract class ShoppingListException extends RuntimeException {

    private final UUID id;
    private final String description;
    private final HttpStatus httpStatus;

    public ShoppingListException(HttpStatus httpStatus, String description) {
        super();
        this.httpStatus = httpStatus;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public ShoppingListException(Throwable cause, HttpStatus httpStatus, String description) {
        super(cause);
        this.httpStatus = httpStatus;
        this.description = description;
        this.id = UUID.randomUUID();
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public Map<String, Object> getErrorResponse() {
        return Map.of(
                "id", UUID.randomUUID(),
                "description", description,
                "error", httpStatus.name(),
                "status", httpStatus.value()
        );
    }

}
