#language: en
#utf-8

@all @delete_shopping_list
Feature: Update shopping list functionality
  User should be able to update shopping lists
  to to add or remove items from the shopping lists

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
  Scenario: Shopping list is updated successfully - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesca shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 12
          },
          {
            "name": "rice",
            "quantity": 1
          },
          {
            "name": "tomatoe",
            "quantity": 10
          }
        ]
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    And the field "name" returned must be "Tesca shopping list"
    And the field "items.0.id" returned must be "null"
    And the field "items.0.name" returned must be "milk"
    And the field "items.0.quantity" returned must be "12"
    And the field "items.1.id" returned must be "null"
    And the field "items.1.name" returned must be "rice"
    And the field "items.1.quantity" returned must be "1"
    And the field "items.2.id" returned must be "null"
    And the field "items.2.name" returned must be "tomatoe"
    And the field "items.2.quantity" returned must be "10"

  @success @response_validation
  Scenario: Shopping list is updated successfully with empty items - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
        ]
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    And the field "name" returned must be "Tesco shopping list"
    And the field "items" returned must be "[]"

  @success @response_validation
  Scenario: Shopping list is updated successfully with no items - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesco shopping list"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    And the field "name" returned must be "Tesco shopping list"
    And the field "items" returned must be "[]"

  @success @db_validation
  Scenario: Shopping list is updated successfully - db validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesca shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 12
          },
          {
            "name": "rice",
            "quantity": 1
          },
          {
            "name": "tomatoe",
            "quantity": 10
          }
        ]
      }
    """
    Then the status returned must be 200
    And the "shopping_list" table must contain 1 row with the "name" "Tesca shopping list"
    And the "items" table must contain 1 row with the "name" "milk"
    And the "items" table must contain 0 row with the "name" "soap"
    And the "items" table must contain 0 row with the "name" "lemon"
    And the "items" table must contain 1 row with the "name" "tomatoe"
    And the "items" table must contain 1 row with the "name" "rice"
    And the "items" table must contain 1 row with the "quantity" "12"
    And the "items" table must contain 1 row with the "quantity" "1"
    And the "items" table must contain 1 row with the "quantity" "10"

  @failure @bad_request_validation
  Scenario Outline: Create shopping list failed - <scenario>
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
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
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 12
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
    When I call the shopping-list update path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f" and the following body
    """
      {
        "name": "Tesco shopping list",
        "items": [
          {
            "name": "milk",
            "quantity": 12
          }
        ]
      }
    """
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"
