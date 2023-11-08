#language: en
#utf-8

@all @delete_shopping_list
Feature: Get shopping list by id functionality
  User should be able to get shopping lists by id
  to see shopping lists he already created

  Background:
    Given the tables are empty
    And the "user" exists
    """
      {
        "username": "john",
        "password": "password",
        "first_name": "John",
        "last_name": "Wick"
      }
    """
    And the "shopping_list" exists
    """
      {
        "id": "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f",
        "name": "Tesco shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 1
          },
          {
            "name": "soap",
            "quantity": 1
          },
          {
            "name": "lemon",
            "quantity": 10
          },
          {
            "name": "tomatoe",
            "quantity": 7
          }
        ]
      }
    """
    And the header is empty

  @success @response_validation
  Scenario: Get Shopping list successfully - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "name" returned must be "Tesco shopping list"
    And the field "items.0.id" returned must be "null"
    And the field "items.0.name" returned must be "lemon"
    And the field "items.0.quantity" returned must be "10"
    And the field "items.1.id" returned must be "null"
    And the field "items.1.name" returned must be "milk"
    And the field "items.1.quantity" returned must be "1"
    And the field "items.2.id" returned must be "null"
    And the field "items.2.name" returned must be "soap"
    And the field "items.2.quantity" returned must be "1"
    And the field "items.3.id" returned must be "null"
    And the field "items.3.name" returned must be "tomatoe"
    And the field "items.3.quantity" returned must be "7"

  @failure @bad_request_validation
  Scenario: Get shopping list failed - shopping list not found
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path with the id "7c467050-034a-474f-b08d-9dd720fb7d10"
    Then the status returned must be 404
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Shopping list not found"
    And the field "status" returned must be "404"
    And the field "error" returned must be "NOT_FOUND"

  @failure @unauthorized_validation
  Scenario: Get shopping list fails when token is wrong
    Given use the token "wrong token" in the header
    When I call the shopping-list get path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"

  @failure @unauthorized_validation
  Scenario: Get shopping list fails when token is not sent
    Given the header is empty
    When I call the shopping-list get path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"
