package org.brienze.shopping.list.api.repository;

import org.brienze.shopping.list.api.model.ShoppingList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.UUID;

public interface ShoppingListRepository extends CrudRepository<ShoppingList, UUID> {
    Collection<ShoppingList> findAllByUserId(UUID userId, PageRequest pageRequest);
}
