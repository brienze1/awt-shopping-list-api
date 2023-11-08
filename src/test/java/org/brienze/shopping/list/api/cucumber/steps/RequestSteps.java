package org.brienze.shopping.list.api.cucumber.steps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.brienze.shopping.list.api.Application;
import org.brienze.shopping.list.api.cucumber.utils.MessageFieldExtractor;
import org.brienze.shopping.list.api.cucumber.utils.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@CucumberContextConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestSteps {

    @LocalServerPort
    private Long port;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtils testUtils;

    @Before
    public void init() {
        testUtils.reset();
    }

    @Given("the header is empty")
    public void the_header_is_empty() {
        testUtils.clearHeader();
    }

    @Given("I call the user login path with the username {string} and password {string}")
    public void i_call_the_user_login_path_with_the_username_and_password(String username, String password) {
        String authorization = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));

        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        testUtils.addHeader(HttpHeaders.AUTHORIZATION, authorization);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/users/authenticate",
                                                                         HttpMethod.POST,
                                                                         new HttpEntity<>(testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @Given("use the token response in the header")
    public void use_the_token_response_in_the_header() throws JsonProcessingException {
        JsonNode response = objectMapper.readTree(testUtils.getResponse().getBody());

        testUtils.addHeader(HttpHeaders.AUTHORIZATION, response.get("token").asText());

        testUtils.clearResponse();
    }

    @Given("use the token {string} in the header")
    public void use_the_token_in_the_header(String value) {
        testUtils.addHeader(HttpHeaders.AUTHORIZATION, value);
    }

    @When("I call the shopping-list creation path with the following body")
    public void i_call_the_shopping_list_creation_path_with_the_following_body(String payload) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists",
                                                                         HttpMethod.POST,
                                                                         new HttpEntity<>(payload, testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @When("I call the user creation path with the following body")
    public void i_call_the_user_creation_path_with_the_following_body(String payload) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/users",
                                                                         HttpMethod.POST,
                                                                         new HttpEntity<>(payload, testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }


    @When("I call the shopping-list deletion path with the id {string}")
    public void i_call_the_shopping_list_deletion_path_with_the_id(String id) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists/" + id,
                                                                         HttpMethod.DELETE,
                                                                         new HttpEntity<>(testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @When("I call the shopping-list get path")
    public void i_call_the_shopping_list_get_path() {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists",
                                                                         HttpMethod.GET,
                                                                         new HttpEntity<>(testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @When("I call the shopping-list get path with page {int} and limit {int}")
    public void i_call_the_shopping_list_get_path_with_page_and_limit(Integer page, Integer limit) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists?page=" + page + "&limit=" + limit,
                                                                         HttpMethod.GET,
                                                                         new HttpEntity<>(testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @When("I call the shopping-list get path with the id {string}")
    public void i_call_the_shopping_list_get_path_with_the_id(String id) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists/" + id,
                                                                         HttpMethod.GET,
                                                                         new HttpEntity<>(testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @When("I call the shopping-list update path with the id {string} and the following body")
    public void i_call_the_shopping_list_update_path_with_the_id_and_the_following_body(String id, String payload) {
        testUtils.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        try {
            ResponseEntity<String> loginResponse = restTemplate.exchange("http://localhost:" + port + "/shopping-lists/" + id,
                                                                         HttpMethod.PUT,
                                                                         new HttpEntity<>(payload, testUtils.getHeaders()),
                                                                         String.class);

            testUtils.setResponse(loginResponse);
        } catch (RestClientResponseException ex) {
            testUtils.setResponseError(ex);
        }
    }

    @Then("the status returned must be {int}")
    public void the_status_returned_must_be(Integer httpStatus) {
        if (testUtils.getResponse() != null) {
            assertEquals(httpStatus, testUtils.getResponse().getStatusCode().value());
        } else {
            assertEquals(httpStatus, testUtils.getResponseError().getStatusCode().value());
        }
    }

    @Then("the field {string} returned must be {string}")
    public void the_field_returned_must_be(String dotSeparatedFields, String value) throws JsonProcessingException {
        JsonNode response;
        if (testUtils.getResponse() != null) {
            response = objectMapper.readTree(testUtils.getResponse().getBody());
        } else {
            response = objectMapper.readTree(testUtils.getResponseError().getResponseBodyAsString());
        }

        String responseValue = MessageFieldExtractor.getResponseFieldValue(response, dotSeparatedFields);

        value = value.equals("empty") ? "" : value;
        value = value.equals("null") ? null : value;

        if (value != null && value.equals("not null")) {
            assertFalse(responseValue.isEmpty());
        } else {
            assertEquals(value, responseValue);
        }
    }

}
