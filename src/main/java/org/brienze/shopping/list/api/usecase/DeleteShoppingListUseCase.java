package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.persistence.ShoppingListPersistence;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteShoppingListUseCase {

    private final ShoppingListPersistence shoppingListPersistence;

    public DeleteShoppingListUseCase(ShoppingListPersistence shoppingListPersistence) {
        this.shoppingListPersistence = shoppingListPersistence;
    }

    public void deleteById(UUID id) {
        shoppingListPersistence.deleteById(id);
    }
}
