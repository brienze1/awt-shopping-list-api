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
        "id": "9f372415-6d18-4b30-9ed0-e2cf07ee98c7",
        "username": "john",
        "password": "password",
        "first_name": "John",
        "last_name": "Wick"
      }
    """
    And the "shopping_list" exists
    """
      {
        "id": "b5d621bf-75aa-4cce-b8f4-6e30a8e5eff1",
        "user_id": "9f372415-6d18-4b30-9ed0-e2cf07ee98c7",
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
    And the "shopping_list" exists
    """
      {
        "id": "bad7c6e9-4413-426f-a9b7-78a308e42162",
        "user_id": "9f372415-6d18-4b30-9ed0-e2cf07ee98c7",
        "name": "Tesco shopping list 2",
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
    And the header is empty

  @success @response_validation
  Scenario: Get Shopping list successfully - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path
    Then the status returned must be 200
    And the field "0.id" returned must be "not null"
    And the field "0.name" returned must be "Tesco shopping list"
    And the field "0.items.0.id" returned must be "null"
    And the field "0.items.0.name" returned must be "lemon"
    And the field "0.items.0.quantity" returned must be "10"
    And the field "0.items.1.id" returned must be "null"
    And the field "0.items.1.name" returned must be "milk"
    And the field "0.items.1.quantity" returned must be "1"
    And the field "0.items.2.id" returned must be "null"
    And the field "0.items.2.name" returned must be "soap"
    And the field "0.items.2.quantity" returned must be "1"
    And the field "0.items.3.id" returned must be "null"
    And the field "0.items.3.name" returned must be "tomatoe"
    And the field "0.items.3.quantity" returned must be "7"
    And the field "1.id" returned must be "not null"
    And the field "1.name" returned must be "Tesco shopping list 2"
    And the field "1.items.0.id" returned must be "null"
    And the field "1.items.0.name" returned must be "milk"
    And the field "1.items.0.quantity" returned must be "12"
    And the field "1.items.1.id" returned must be "null"
    And the field "1.items.1.name" returned must be "rice"
    And the field "1.items.1.quantity" returned must be "1"
    And the field "1.items.2.id" returned must be "null"
    And the field "1.items.2.name" returned must be "tomatoe"
    And the field "1.items.2.quantity" returned must be "10"

  @success @response_validation
  Scenario: Get Shopping list successfully by page and limit - page 0 and limit 1 response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path with page 0 and limit 1
    Then the status returned must be 200
    And the field "0.id" returned must be "not null"
    And the field "0.name" returned must be "Tesco shopping list"
    And the field "0.items.0.id" returned must be "null"
    And the field "0.items.0.name" returned must be "lemon"
    And the field "0.items.0.quantity" returned must be "10"
    And the field "0.items.1.id" returned must be "null"
    And the field "0.items.1.name" returned must be "milk"
    And the field "0.items.1.quantity" returned must be "1"
    And the field "0.items.2.id" returned must be "null"
    And the field "0.items.2.name" returned must be "soap"
    And the field "0.items.2.quantity" returned must be "1"
    And the field "0.items.3.id" returned must be "null"
    And the field "0.items.3.name" returned must be "tomatoe"
    And the field "0.items.3.quantity" returned must be "7"

  @success @response_validation
  Scenario: Get Shopping list successfully by page and limit - page 1 and limit 1 response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path with page 1 and limit 1
    Then the status returned must be 200
    And the field "0.id" returned must be "not null"
    And the field "0.name" returned must be "Tesco shopping list 2"
    And the field "0.items.0.id" returned must be "null"
    And the field "0.items.0.name" returned must be "milk"
    And the field "0.items.0.quantity" returned must be "12"
    And the field "0.items.1.id" returned must be "null"
    And the field "0.items.1.name" returned must be "rice"
    And the field "0.items.1.quantity" returned must be "1"
    And the field "0.items.2.id" returned must be "null"
    And the field "0.items.2.name" returned must be "tomatoe"
    And the field "0.items.2.quantity" returned must be "10"

  @success @response_validation
  Scenario: Get Shopping list successfully empty list - response validation
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
    And I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path
    Then the status returned must be 200
    And the field "0" returned must be "null"

  @failure @bad_request_validation
  Scenario Outline: Get shopping list success ignoring page or limit invalid - <scenario>
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list get path with page <page> and limit <limit>
    Then the status returned must be 200
    And the field "0.id" returned must be "not null"
    And the field "0.name" returned must be "Tesco shopping list"
    And the field "0.items.0.id" returned must be "null"
    And the field "0.items.0.name" returned must be "lemon"
    And the field "0.items.0.quantity" returned must be "10"
    And the field "0.items.1.id" returned must be "null"
    And the field "0.items.1.name" returned must be "milk"
    And the field "0.items.1.quantity" returned must be "1"
    And the field "0.items.2.id" returned must be "null"
    And the field "0.items.2.name" returned must be "soap"
    And the field "0.items.2.quantity" returned must be "1"
    And the field "0.items.3.id" returned must be "null"
    And the field "0.items.3.name" returned must be "tomatoe"
    And the field "0.items.3.quantity" returned must be "7"
    And the field "1.id" returned must be "not null"
    And the field "1.name" returned must be "Tesco shopping list 2"
    And the field "1.items.0.id" returned must be "null"
    And the field "1.items.0.name" returned must be "milk"
    And the field "1.items.0.quantity" returned must be "12"
    And the field "1.items.1.id" returned must be "null"
    And the field "1.items.1.name" returned must be "rice"
    And the field "1.items.1.quantity" returned must be "1"
    And the field "1.items.2.id" returned must be "null"
    And the field "1.items.2.name" returned must be "tomatoe"
    And the field "1.items.2.quantity" returned must be "10"

    Examples:
      | scenario             | page | limit |
      | page negative        | -1   | 10    |
      | limit zero           | 0    | 0     |
      | limit less than zero | 0    | -1    |

  @failure @unauthorized_validation
  Scenario: Get shopping list fails when token is wrong
    Given use the token "wrong token" in the header
    When I call the shopping-list get path
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"

  @failure @unauthorized_validation
  Scenario: Get shopping list fails when token is not sent
    Given the header is empty
    When I call the shopping-list get path
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"
