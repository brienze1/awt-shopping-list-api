package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Item {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private Integer quantity;

}
