package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "item", schema = "shopping_list")
public class Item {

    public Item() {
    }

    public Item(ShoppingList shoppingList, Item item) {
        this.shoppingList = shoppingList;
        this.name = item.name;
        this.quantity = item.quantity;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShoppingList shoppingList;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Integer quantity;

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
