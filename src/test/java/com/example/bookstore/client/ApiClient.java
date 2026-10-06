package com.example.bookstore.client;

import com.example.bookstore.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiClient {

    public ApiClient() {
        RestAssured.baseURI = ApiConfig.baseUrl();
        RestAssured.reset();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public Response get(String path) {
        return given().when().get(path).then().extract().response();
    }

    public Response post(String path, Object body) {
        return given().contentType("application/json").body(body)
                .when().post(path).then().extract().response();
    }

    public Response put(String path, Object body) {
        return given().contentType("application/json").body(body)
                .when().put(path).then().extract().response();
    }

    public Response delete(String path) {
        return given().when().delete(path).then().extract().response();
    }
}
