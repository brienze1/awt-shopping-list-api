package org.brienze.shopping.list.api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {

    @PostMapping
    public void create() {

    }

    @PutMapping
    public void edit() {

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
