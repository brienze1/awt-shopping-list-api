#language: en
#utf-8

@all @create_user
Feature: Create user functionality
  User should be able to create its user
  to authenticate in the system later on

  Background:
    Given the tables are empty

  @success @response_validation
  Scenario: User is created successfully - response validation
    When I call the user creation path with the following body
    """
      {
        "username": "brienze",
        "password": "password",
        "first_name": "Luis",
        "last_name": "Brienze"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "first_name" returned must be "Luis"
    And the field "last_name" returned must be "Brienze"

  @success @db_validation
  Scenario: User is created successfully - db validation
    When I call the user creation path with the following body
    """
      {
        "username": "brienze",
        "password": "password",
        "first_name": "Luis",
        "last_name": "Brienze"
      }
    """
    Then the status returned must be 201
    And the "user" table must contain 1 row with the "first_name" "Luis"
    And the "user" table must contain 1 row with the "last_name" "Brienze"
    And the "user_credential" table must contain 1 row with the "username" "brienze"

  @failure @bad_request_validation
  Scenario Outline: Create operation failed - <scenario>
    When I call the user creation path with the following body
    """
      {
        "username": "<username>",
        "password": "<password>",
        "first_name": "<first_name>",
        "last_name": "<last_name>"
      }
    """
    Then the status returned must be 400
    And the field "id" returned must be "not null"
    And the field "description" returned must be "<description>"
    And the field "status" returned must be "400"
    And the field "error" returned must be "BAD_REQUEST"

    Examples:
      | scenario         | username | password | first_name | last_name | description              |
      | username empty   |          | password | first_name | last_name | Username must be valid   |
      | password empty   | username |          | first_name | last_name | Password must be valid   |
      | first_name empty | username | password |            | last_name | First name must be valid |
      | last_name empty  | username | password | first_name |           | Last name must be valid  |

  @failure @username_taken
  Scenario: Create user fails when username already exists
    Given the "user" exists
    """
      {
        "username": "john",
        "password": "password",
        "first_name": "John>",
        "last_name": "Wick"
      }
    """
    When I call the user creation path with the following body
    """
      {
        "username": "john",
        "password": "123abc",
        "first_name": "John",
        "last_name": "Doe"
      }
    """
    Then the status returned must be 409
    And the field "id" returned must be "not null"
    And the field "description" returned must be "Username not available"
    And the field "status" returned must be "409"
    And the field "error" returned must be "CONFLICT"
