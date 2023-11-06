package org.brienze.shopping.list.api.persistence;

import org.brienze.shopping.list.api.model.ShoppingList;
import org.brienze.shopping.list.api.repository.ShoppingListRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ShoppingListPersistence {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListPersistence(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingList save(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    public Optional<ShoppingList> findById(UUID id) {
        return shoppingListRepository.findById(id);
    }
}
