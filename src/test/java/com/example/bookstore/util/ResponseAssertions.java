package com.example.bookstore.util;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class ResponseAssertions {
    private ResponseAssertions() { }

    public static void assertJsonObject(Response response, String... fields) {
        assertTrue(response.contentType().toLowerCase().contains("json"), "Expected JSON response");
        Map<String, Object> body = response.as(Map.class);
        assertNotNull(body, "Response body must be a JSON object");
        for (String field : fields) assertTrue(body.containsKey(field), "Missing field: " + field);
    }

    public static void assertJsonArray(Response response) {
        assertTrue(response.contentType().toLowerCase().contains("json"), "Expected JSON response");
        List<?> body = response.as(List.class);
        assertNotNull(body, "Response body must be a JSON array");
    }
}
