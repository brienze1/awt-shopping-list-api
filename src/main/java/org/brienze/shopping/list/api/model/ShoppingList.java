package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import org.brienze.shopping.list.api.exception.InvalidField;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "shopping_list", schema = "shopping_list")
public class ShoppingList {

    @Id
    @Column(name = "id")
    @JsonProperty(value = "id")
    private UUID id;

    @Column(name = "user_id")
    @JsonProperty(value = "user_id", access = JsonProperty.Access.WRITE_ONLY)
    private UUID userId;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shoppingList", orphanRemoval = true)
    @JsonProperty("items")
    private Set<Item> items;

    @PrePersist
    protected void onCreate() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID();
        }
    }

    @JsonGetter
    public String getName() {
        return this.name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name =
                Optional.ofNullable(name).filter(value -> !value.isBlank()).orElseThrow(() -> new InvalidField("Shopping list name must be valid"));
    }

    @JsonSetter("items")
    public void setItems(Set<Item> items) {
        this.items = Optional.ofNullable(this.items).orElse(new HashSet<>());
        this.items.clear();
        this.items.addAll(Optional.ofNullable(items).orElse(new HashSet<>()).stream().map(item -> new Item(this, item)).collect(Collectors.toSet()));
    }

    @JsonGetter("items")
    public Set<Item> getItems() {
        return Optional.ofNullable(items)
                       .orElse(new HashSet<>())
                       .stream()
                       .sorted(Comparator.comparing(Item::getName))
                       .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public UUID getId() {
        return id;
    }

    public void setUser(User user) {
        this.userId = user.getId();
    }

    public void updateFrom(ShoppingList shoppingList) {
        this.name = Optional.ofNullable(shoppingList).map(ShoppingList::getName).orElse(this.name);
        this.items.clear();
        Optional.ofNullable(shoppingList)
                .map(ShoppingList::getItems)
                .orElse(new HashSet<>())
                .stream()
                .peek(item -> item.setShoppingList(this))
                .forEach(item -> this.items.add(item));
    }
}
