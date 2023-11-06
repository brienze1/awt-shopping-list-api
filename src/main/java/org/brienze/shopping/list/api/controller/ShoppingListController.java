package org.brienze.shopping.list.api.controller;

import org.brienze.shopping.list.api.annotation.Authorized;
import org.brienze.shopping.list.api.model.ShoppingList;
import org.brienze.shopping.list.api.model.User;
import org.brienze.shopping.list.api.usecase.GetShoppingListUseCase;
import org.brienze.shopping.list.api.usecase.GetUserUseCase;
import org.brienze.shopping.list.api.usecase.SaveShoppingListUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    private final GetUserUseCase getUserUseCase;
    private final SaveShoppingListUseCase saveShoppingListUseCase;
    private final GetShoppingListUseCase getShoppingListUseCase;

    public ShoppingListController(GetUserUseCase getUserUseCase,
                                  SaveShoppingListUseCase saveShoppingListUseCase,
                                  GetShoppingListUseCase getShoppingListUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.saveShoppingListUseCase = saveShoppingListUseCase;
        this.getShoppingListUseCase = getShoppingListUseCase;
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
    public void getAll(@RequestHeader("Authorization") String token, @RequestParam("page") Long page, @RequestParam("limit") Long limit) {

    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getOne(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(getShoppingListUseCase.findById(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {

    }


}
