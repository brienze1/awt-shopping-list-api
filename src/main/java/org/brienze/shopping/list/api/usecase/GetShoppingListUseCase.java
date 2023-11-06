package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.exception.ShoppingListNotFound;
import org.brienze.shopping.list.api.model.ShoppingList;
import org.brienze.shopping.list.api.persistence.ShoppingListPersistence;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetShoppingListUseCase {

    private final ShoppingListPersistence shoppingListPersistence;

    public GetShoppingListUseCase(ShoppingListPersistence shoppingListPersistence) {
        this.shoppingListPersistence = shoppingListPersistence;
    }

    public ShoppingList findById(UUID id) {
        return shoppingListPersistence.findById(id).orElseThrow(ShoppingListNotFound::new);
    }

}
