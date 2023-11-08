package org.brienze.shopping.list.api.cucumber.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.brienze.shopping.list.api.cucumber.utils.MessageFieldExtractor;
import org.brienze.shopping.list.api.model.*;
import org.brienze.shopping.list.api.repository.ShoppingListRepository;
import org.brienze.shopping.list.api.repository.UserAuthorizationRepository;
import org.brienze.shopping.list.api.repository.UserCredentialRepository;
import org.brienze.shopping.list.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DbSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private UserAuthorizationRepository userAuthorizationRepository;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Given("the tables are empty")
    public void the_tables_are_empty() {
        shoppingListRepository.deleteAll();
        userAuthorizationRepository.deleteAll();
        userCredentialRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the {string} exists")
    public void the_exists(String entity, String jsonEntity) throws JsonProcessingException {
        switch (entity) {
            case "user":
                CreateUserRequest createUserRequest = objectMapper.readValue(jsonEntity, CreateUserRequest.class);
                User user = new User(createUserRequest);
                userRepository.save(user);
                break;
            case "shopping_list":
                ShoppingList shoppingList = objectMapper.readValue(jsonEntity, ShoppingList.class);
                shoppingListRepository.save(shoppingList);
                break;
            default:
                throw new RuntimeException("entity not mapped");
        }
    }

    @Then("the {string} table must contain {int} row with the {string} {string}")
    public void the_table_must_contain_row_with_the(String table, Integer quantity, String row, String value) throws JsonProcessingException {
        Set<JsonNode> rows = new HashSet<>();
        switch (table) {
            case "user":
                for (User user : userRepository.findAll()) {
                    String userString = objectMapper.writeValueAsString(user);
                    JsonNode json = objectMapper.readTree(userString);
                    rows.add(json);
                }
                break;
            case "user_credential":
                for (UserCredential userCredential : userCredentialRepository.findAll()) {
                    String userCredentialString = objectMapper.writeValueAsString(userCredential);
                    JsonNode json = objectMapper.readTree(userCredentialString);
                    rows.add(json);
                }
                break;
            case "shopping_list":
                for (ShoppingList shoppingList : shoppingListRepository.findAll()) {
                    String shopingListString = objectMapper.writeValueAsString(shoppingList);
                    JsonNode json = objectMapper.readTree(shopingListString);
                    rows.add(json);
                }
                break;
            case "items":
                Set<Item> items = StreamSupport.stream(shoppingListRepository.findAll().spliterator(), false)
                                               .flatMap(shoppingList -> shoppingList.getItems().stream())
                                               .collect(Collectors.toSet());
                for (Item item : items) {
                    String itemString = objectMapper.writeValueAsString(item);
                    JsonNode json = objectMapper.readTree(itemString);
                    rows.add(json);
                }
                break;
            case "user_authorization":
                for (UserAuthorization userAuthorization : userAuthorizationRepository.findAll()) {
                    String userAuthorizationString = objectMapper.writeValueAsString(userAuthorization);
                    JsonNode json = objectMapper.readTree(userAuthorizationString);
                    rows.add(json);
                }
                break;
            default:
                throw new RuntimeException("entity not mapped");
        }

        Integer count = 0;
        for (JsonNode entity : rows) {
            if ((value.equals("not null") && !MessageFieldExtractor.getResponseFieldValue(entity, row).isBlank()) ||
                MessageFieldExtractor.getResponseFieldValue(entity, row).equals(value)) {
                count++;
            }
        }

        assertEquals(quantity, count);
    }

}
