package com.example.bookstore.tests;

import com.example.bookstore.client.ApiClient;
import com.example.bookstore.client.BooksClient;
import com.example.bookstore.data.Book;
import com.example.bookstore.util.ReportExtension;
import com.example.bookstore.util.ResponseAssertions;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Books API")
@ExtendWith(ReportExtension.class)
class BooksApiTest {
    private BooksClient books;

    @BeforeEach
    void setUp() {
        books = new BooksClient(new ApiClient());
    }

    @Test
    @DisplayName("GET /Books returns a non-empty JSON array")
    void getAllBooks() {
        Response response = books.getAll();
        assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonArray(response);
        assertFalse(response.jsonPath().getList("$").isEmpty());
    }

    @Test
    @DisplayName("POST /Books accepts a valid book payload")
    void createBook() {
        Book book = Book.valid(9999);
        Response response = books.create(book);
        assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonObject(response, "id", "title", "description", "pageCount", "excerpt", "publishDate");
        assertEquals(book.title(), response.jsonPath().getString("title"));
        assertEquals(book.pageCount(), response.jsonPath().getInt("pageCount"));
    }

    @Test @DisplayName("PUT /Books/{id} returns the supplied updated representation")
    void updateBook() {
        int id = 1;
        Book updated = Book.updated(id);
        Response response = books.update(id, updated);
       assertEquals(200, response.statusCode());
        ResponseAssertions.assertJsonObject(response, "id", "title", "description", "pageCount", "excerpt", "publishDate");
        assertEquals(id, response.jsonPath().getInt("id"));
       assertEquals(updated.title(), response.jsonPath().getString("title"));
    }

}
