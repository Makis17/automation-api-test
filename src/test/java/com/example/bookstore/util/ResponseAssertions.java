package com.example.bookstore.util;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ResponseAssertions {

    private ResponseAssertions() {
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertEquals(
                expectedStatusCode,
                response.statusCode(),
                "Unexpected HTTP status code"
        );
    }
}