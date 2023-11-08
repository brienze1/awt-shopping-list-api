#language: en
#utf-8

@all @user_login
Feature: User login functionality
  User should be able to login using username and password
  to get permission to use other routes

  Background:
    Given the tables are empty

  @success @response_validation
  Scenario: User login successful - response validation
    Given the "user" exists
    """
      {
        "username": "john",
        "password": "password",
        "first_name": "John",
        "last_name": "Wick"
      }
    """
    When I call the user login path with the username "john" and password "password"
    Then the status returned must be 200
    And the field "token" returned must be "not null"
    And the field "issued_at" returned must be "not null"
    And the field "expires_at" returned must be "not null"

  @success @db_validation
  Scenario: User login successful - db validation
    Given the "user" exists
    """
      {
        "username": "john",
        "password": "password",
        "first_name": "John",
        "last_name": "Wick"
      }
    """
    When I call the user login path with the username "john" and password "password"
    Then the status returned must be 200
    And the "user_authorization" table must contain 1 row with the "token" "not null"
    And the "user_authorization" table must contain 1 row with the "issued_at" "not null"
    And the "user_authorization" table must contain 1 row with the "expires_at" "not null"

  @failure
  Scenario: User login failure - user does not exist
    When I call the user login path with the username "john" and password "password"
    Then the status returned must be 409
    And the field "id" returned must be "not null"
    And the field "description" returned must be "User not authorized"
    And the field "status" returned must be "409"
    And the field "error" returned must be "CONFLICT"

  @failure
  Scenario: User login failure - user password is wrong
    Given the "user" exists
    """
      {
        "username": "john",
        "password": "password",
        "first_name": "John",
        "last_name": "Wick"
      }
    """
    When I call the user login path with the username "john" and password "aaabbb123"
    Then the status returned must be 409
    And the field "id" returned must be "not null"
    And the field "description" returned must be "User not authorized"
    And the field "status" returned must be "409"
    And the field "error" returned must be "CONFLICT"
