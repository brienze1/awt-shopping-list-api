package org.brienze.shopping.list.api.cucumber.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
public class TestUtils {

    private ResponseEntity<String> response;
    private RestClientResponseException responseError;
    private HttpHeaders headers = new HttpHeaders();

    public void reset() {
        setResponse(null);
        setResponseError(null);
        this.headers = new HttpHeaders();
    }

    public ResponseEntity<String> getResponse() {
        return response;
    }

    public RestClientResponseException getResponseError() {
        return responseError;
    }

    public void clearResponse() {
        this.response = null;
        this.responseError = null;
    }

    public void setResponse(ResponseEntity<String> response) {
        this.response = response;
    }

    public void setResponseError(RestClientResponseException responseError) {
        this.responseError = responseError;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void addHeader(String header, String value) {
        if (this.headers.containsKey(header)) {
            headers.remove(header);
        }
        this.headers.add(header, value);
    }

    public void clearHeader() {
        this.headers.clear();
    }

}
