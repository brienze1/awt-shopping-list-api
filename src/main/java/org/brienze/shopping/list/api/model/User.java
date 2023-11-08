package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.brienze.shopping.list.api.exception.InvalidField;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "shopping_list")
public class User {

    public User() {
    }

    public User(CreateUserRequest createUserRequest) {
        this.id = Optional.ofNullable(createUserRequest.getId()).orElse(UUID.randomUUID());
        this.firstName = Optional.ofNullable(createUserRequest.getFirstName())
                                 .filter(value -> !value.isBlank())
                                 .orElseThrow(() -> new InvalidField("First name must be valid"));
        this.lastName = Optional.ofNullable(createUserRequest.getLastName())
                                .filter(value -> !value.isBlank())
                                .orElseThrow(() -> new InvalidField("Last name must be valid"));
        this.userCredential = new UserCredential(this, createUserRequest.getUsername(), createUserRequest.getPassword());
    }

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("last_name")
    private String lastName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCredential userCredential;

    public UUID getId() {
        return id;
    }
}
