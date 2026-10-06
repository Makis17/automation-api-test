package com.example.bookstore.client;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestAssuredApiClient implements ApiClient {


    private final RequestSpecification requestSpecification;

    public RestAssuredApiClient(String baseUrl) {
        this.requestSpecification = RestAssured
                .given()
                .baseUri(baseUrl)
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());
    }

    @Override
    public Response get(String path) {
        return request()
                .when()
                .get(path);
    }

    @Override
    public Response post(String path, Object body) {
        return request()
                .contentType("application/json")
                .body(body)
                .when()
                .post(path);
    }

    @Override
    public Response put(String path, Object body) {
        return request()
                .contentType("application/json")
                .body(body)
                .when()
                .put(path);
    }

    @Override
    public Response delete(String path) {
        return request()
                .when()
                .delete(path);
    }

    private RequestSpecification request() {
        return RestAssured
                .given()
                .spec(requestSpecification);
    }
}
