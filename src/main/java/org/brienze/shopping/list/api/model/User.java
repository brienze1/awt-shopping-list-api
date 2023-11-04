package org.brienze.shopping.list.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user", schema = "shopping_list")
public class User {

    public User() {
    }

    public User(CreateUserRequest createUserRequest) {
        this.firstName = createUserRequest.getFirstName();
        this.lastName = createUserRequest.getLastName();
        this.userCredential = new UserCredential(this, createUserRequest.getUsername(), createUserRequest.getPassword());
    }

    @Id
    @GeneratedValue
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

}
