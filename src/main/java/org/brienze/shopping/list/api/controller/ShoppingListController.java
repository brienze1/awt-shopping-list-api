package org.brienze.shopping.list.api.controller;

import org.brienze.shopping.list.api.annotation.Authorized;
import org.brienze.shopping.list.api.model.ShoppingList;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.usecase.DeleteShoppingListUseCase;
import org.brienze.shopping.list.api.usecase.GetShoppingListUseCase;
import org.brienze.shopping.list.api.usecase.GetUserUseCase;
import org.brienze.shopping.list.api.usecase.SaveShoppingListUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    private final GetUserUseCase getUserUseCase;
    private final SaveShoppingListUseCase saveShoppingListUseCase;
    private final GetShoppingListUseCase getShoppingListUseCase;
    private final DeleteShoppingListUseCase deleteShoppingListUseCase;

    public ShoppingListController(GetUserUseCase getUserUseCase,
                                  SaveShoppingListUseCase saveShoppingListUseCase,
                                  GetShoppingListUseCase getShoppingListUseCase,
                                  DeleteShoppingListUseCase deleteShoppingListUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.saveShoppingListUseCase = saveShoppingListUseCase;
        this.getShoppingListUseCase = getShoppingListUseCase;
        this.deleteShoppingListUseCase = deleteShoppingListUseCase;
    }

    @PostMapping
    @Authorized
    public ResponseEntity<ShoppingList> create(@RequestHeader("Authorization") String token, @RequestBody ShoppingList shoppingList) {
        User user = getUserUseCase.findByToken(token);

        shoppingList.setUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveShoppingListUseCase.save(shoppingList));
    }

    @PutMapping("/{id}")
    @Authorized
    public ResponseEntity<ShoppingList> edit(@PathVariable("id") UUID id, @RequestBody ShoppingList shoppingList) {
        ShoppingList existingShoppingList = getShoppingListUseCase.findById(id);

        existingShoppingList.updateFrom(shoppingList);

        return ResponseEntity.status(HttpStatus.OK).body(saveShoppingListUseCase.save(existingShoppingList));

    }

    @GetMapping
    @Authorized
    public ResponseEntity<Collection<ShoppingList>> getAll(@RequestHeader("Authorization") String token,
                                                           @RequestParam(value = "page", required = false) Integer page,
                                                           @RequestParam(value = "limit", required = false) Integer limit) {
        User user = getUserUseCase.findByToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(getShoppingListUseCase.findByUserIdPageable(
                user.getId(),
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(limit).orElse(10)
        ));
    }

    @GetMapping("/{id}")
    @Authorized
    public ResponseEntity<ShoppingList> getOne(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(getShoppingListUseCase.findById(id));
    }

    @DeleteMapping("/{id}")
    @Authorized
    public void delete(@PathVariable("id") UUID id) {
        deleteShoppingListUseCase.deleteById(id);
    }


}
