package org.brienze.shopping.list.api.controller;

import org.brienze.shopping.list.api.annotation.Authorized;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    @PostMapping
    @Authorized
    public void create() {
        System.out.println("Created");
    }

    @PutMapping
    public void edit() {
        System.out.println("Edit");

    }

    @GetMapping
    public void getAll() {

    }

    @GetMapping("/{id}")
    public void getOne(@PathVariable("id") UUID id) {
        System.out.println(id);
    }

    @DeleteMapping
    public void delete() {

    }


}
