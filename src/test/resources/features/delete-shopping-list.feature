#language: en
#utf-8

@all @delete_shopping_list
Feature: Delete shopping list functionality
  User should be able to delete shopping lists
  to remove shopping lists he already used

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
  Scenario: Shopping list is deleted successfully - response validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list deletion path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 200

  @success @db_validation
  Scenario: Shopping list is deleted successfully - db validation
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list deletion path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 200
    And the "shopping_list" table must contain 0 row with the "name" "Tesco shopping list"

  @failure @bad_request_validation
  Scenario: Delete shopping list failed - shopping list not found
    Given I call the user login path with the username "john" and password "password"
    And use the token response in the header
    When I call the shopping-list deletion path with the id "7c467050-034a-474f-b08d-9dd720fb7d10"
    Then the status returned must be 200

  @failure @unauthorized_validation
  Scenario: Create shopping list fails when token is wrong
    Given use the token "wrong token" in the header
    When I call the shopping-list deletion path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"

  @failure @unauthorized_validation
  Scenario: Create shopping list fails when token is not sent
    Given the header is empty
    When I call the shopping-list deletion path with the id "3ec448f1-dcf4-4f09-ae32-24eb56b4ba3f"
    Then the status returned must be 403
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Token is invalid"
    And the field "status" returned must be "403"
    And the field "error" returned must be "FORBIDDEN"
