package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import org.brienze.shopping.list.api.exception.InvalidField;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "item", schema = "shopping_list")
public class Item {

    public Item() {
    }

    protected Item(ShoppingList shoppingList, Item item) {
        this.shoppingList = shoppingList;
        this.name = Optional.ofNullable(item.name).filter(value -> !value.isBlank()).orElseThrow(() -> new InvalidField("Item name must be valid"));
        this.quantity =
                Optional.ofNullable(item.quantity).filter(value -> value > 0).orElseThrow(() -> new InvalidField("Item quantity must be valid"));
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonIgnore
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingList shoppingList;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Integer quantity;

    protected void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    protected String getName() {
        return name;
    }

    @JsonSetter
    public void setName(String name) {
        this.name = Optional.ofNullable(name).filter(value -> !value.isBlank()).orElseThrow(() -> new InvalidField("Item name must be valid"));
    }

    @JsonSetter
    public void setQuantity(Integer quantity) {
        this.quantity =
                Optional.ofNullable(quantity).filter(value -> value > 0).orElseThrow(() -> new InvalidField("Item quantity must be valid"));
    }
}
