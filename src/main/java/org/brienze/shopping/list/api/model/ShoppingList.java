package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "shopping_list", schema = "shopping_list")
public class ShoppingList {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Column(name = "user_id")
    @JsonIgnore
    private UUID userId;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shoppingList", orphanRemoval = true)
    @JsonProperty("items")
    private Set<Item> items;

    @JsonSetter("items")
    public void setItems(Set<Item> items) {
        this.items = Optional.ofNullable(this.items).orElse(new HashSet<>());
        this.items.clear();
        this.items.addAll(Optional.ofNullable(items).orElse(new HashSet<>()).stream().map(item -> new Item(this, item)).collect(Collectors.toSet()));
    }

    public UUID getId() {
        return id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setUser(User user) {
        this.userId = user.getId();
    }

    public void updateFrom(ShoppingList shoppingList) {
        this.items.clear();
        Optional.ofNullable(shoppingList)
                .map(ShoppingList::getItems)
                .orElse(new HashSet<>())
                .stream()
                .peek(item -> item.setShoppingList(this))
                .forEach(item -> this.items.add(item));
    }
}
