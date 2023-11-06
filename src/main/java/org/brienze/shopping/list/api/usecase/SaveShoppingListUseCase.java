package org.brienze.shopping.list.api.usecase;

import org.brienze.shopping.list.api.model.ShoppingList;
import org.brienze.shopping.list.api.persistence.ShoppingListPersistence;
import org.springframework.stereotype.Component;

@Component
public class SaveShoppingListUseCase {

    private final ShoppingListPersistence shoppingListPersistence;

    public SaveShoppingListUseCase(ShoppingListPersistence shoppingListPersistence) {
        this.shoppingListPersistence = shoppingListPersistence;
    }

    public ShoppingList save(ShoppingList shoppingList) {
        return shoppingListPersistence.save(shoppingList);
    }

}
