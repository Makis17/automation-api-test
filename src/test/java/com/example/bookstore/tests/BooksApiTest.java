package com.example.bookstore.tests;

import com.example.bookstore.data.Book;
import com.example.bookstore.data.BookTestData;
import com.example.bookstore.util.ReportExtension;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Books API")
@ExtendWith(ReportExtension.class)
class BooksApiTest extends BaseApiTest {


    @Test
    @DisplayName("Retrieval of book by id")
    void getBookById() {

        int bookId = 1;

        Response response = booksClient.getById(bookId);

        assertEquals(200, response.statusCode());
        assertEquals(bookId, response.jsonPath().getInt("id"));
    }

    @Test
    @DisplayName("Create a book")
    void createBook() {

        Book book = BookTestData.valid();

        Response response = booksClient.create(book);

        assertEquals(200, response.statusCode());
        assertEquals(book.id(), response.jsonPath().getInt("id"));
        assertEquals(book.title(), response.jsonPath().getString("title"));
    }

    @Test
    @DisplayName("Update existing book")
    void updateBook() {

        int bookId = 1;

        Book book = BookTestData.updated(bookId);

        Response response = booksClient.update(bookId, book);

        assertEquals(200, response.statusCode());
        assertEquals(bookId, response.jsonPath().getInt("id"));
        assertEquals(
                "The API Automation Handbook - Updated",
                response.jsonPath().getString("title")
        );
    }

    @Test
    @DisplayName("Delete book")
    void deleteBook() {

        Response response = booksClient.delete(1);

        assertEquals(200, response.statusCode());
    }

}
