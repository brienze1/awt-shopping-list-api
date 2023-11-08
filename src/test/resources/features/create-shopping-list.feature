#language: en
#utf-8

@all @create_shopping_list
Feature: Create shopping list functionality
  User should be able to create shopping lists
  to save his products for latter on

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
    And the header is empty

  @success @response_validation
  Scenario: Shopping list is created successfully - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list creation path with the following body
    """
      {
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
    Then the status returned must be 201
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

  @success @response_validation
  Scenario: Shopping list is created successfully with empty items - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list creation path with the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
        ]
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "name" returned must be "Tesco shopping list"
    And the field "items" returned must be "[]"

  @success @response_validation
  Scenario: Shopping list is created successfully with no items - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list creation path with the following body
    """
      {
        "name": "Tesco shopping list"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "name" returned must be "Tesco shopping list"
    And the field "items" returned must be "[]"

  @success @db_validation
  Scenario: Shopping list is created successfully - db validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list creation path with the following body
    """
      {
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
    Then the status returned must be 201
    And the "shopping_list" table must contain 1 row with the "name" "Tesco shopping list"
    And the "items" table must contain 1 row with the "name" "milk"
    And the "items" table must contain 1 row with the "name" "soap"
    And the "items" table must contain 1 row with the "name" "lemon"
    And the "items" table must contain 1 row with the "name" "tomatoe"
    And the "items" table must contain 2 row with the "quantity" "1"
    And the "items" table must contain 1 row with the "quantity" "10"
    And the "items" table must contain 1 row with the "quantity" "7"

  @failure @bad_request_validation
  Scenario Outline: Create shopping list failed - <scenario>
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list creation path with the following body
    """
      {
        "name": "<name>",
        "items": [
          {
            "name": "<product>",
            "quantity": <quantity>
          }
        ]
      }
    """
    Then the status returned must be 400
    And the field "id" returned must be "not null"
    And the field "description" returned must be "<description>"
    And the field "status" returned must be "400"
    And the field "error" returned must be "BAD_REQUEST"

    Examples:
      | scenario                | name                | product | quantity | description                      |
      | list name empty         |                     | lemon   | 1        | Shopping list name must be valid |
      | item name empty         | Tesco shopping list |         | 1        | Item name must be valid          |
      | quantity zero           | username            | lemon   | 0        | Item quantity must be valid      |
      | quantity less than zero | username            | lemon   | -1       | Item quantity must be valid      |

  @failure @unauthorized_validation
  Scenario: Create shopping list fails when token is wrong
    Given use the token "wrong token" in the header
    When I call the shopping-list creation path with the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 1
          }
        ]
      }
    """
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"

  @failure @unauthorized_validation
  Scenario: Create shopping list fails when token is not sent
    Given the header is empty
    When I call the shopping-list creation path with the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 1
          }
        ]
      }
    """
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"
