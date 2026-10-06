package com.example.bookstore.tests;

import com.example.bookstore.client.ApiClient;
import com.example.bookstore.client.AuthorsClient;
import com.example.bookstore.data.Author;
import com.example.bookstore.util.ReportExtension;
import com.example.bookstore.util.ResponseAssertions;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Authors API - Bonus")
@ExtendWith(ReportExtension.class)
class AuthorsApiTest {
    private AuthorsClient authors;

    @BeforeEach void setUp() {
        authors = new AuthorsClient(new ApiClient());
    }

    @Test @DisplayName("GET /Authors returns a non-empty JSON array")
    void getAllAuthors() {
        Response response = authors.getAll();
        assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonArray(response);
        assertFalse(response.jsonPath().getList("$").isEmpty());
    }


    @Test @DisplayName("POST /Authors accepts a valid author payload")
    void createAuthor() {
        Author author = Author.valid(9999);
        Response response = authors.create(author);
        assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonObject(response, "id", "idBook", "firstName", "lastName");
        assertEquals(author.firstName(), response.jsonPath().getString("firstName"));
    }

    @Test @DisplayName("PUT /Authors/{id} returns the updated representation")
    void updateAuthor() {
        int id = 1;
        Author author = Author.updated(id);
        Response response = authors.update(id, author);
        assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonObject(response, "id", "idBook", "firstName", "lastName");
        assertEquals(author.firstName(), response.jsonPath().getString("firstName"));
        assertEquals(author.lastName(), response.jsonPath().getString("lastName"));
    }

}
