package com.example.bookstore.tests;

import com.example.bookstore.data.Book;
import com.example.bookstore.data.BookTestData;
import com.example.bookstore.util.ReportExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.example.bookstore.util.ResponseAssertions.assertResponseTitle;
import static com.example.bookstore.util.ResponseAssertions.assertStatusCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Books API")
@ExtendWith(ReportExtension.class)
class BooksApiTest extends BaseApiTest {

    @Test
    @DisplayName("Retrieve all books")
    void getAllBooks() {

        Response response = booksClient.getAll();

        assertStatusCode(response, 200);
    }

    @Test
    @DisplayName("Retrieve book by id")
    void getBookById() {

        int bookId = 1;

        Response response = booksClient.getById(bookId);

        assertStatusCode(response, 200);

        assertEquals(
                bookId,
                response.jsonPath().getInt("id")
        );
    }

    @Test
    @DisplayName("Retrieve book by id that does not exist")
    void getBookByIdInvalid() {

        int bookId = -1;

        Response response = booksClient.getById(bookId);

        assertStatusCode(response, 404);

        assertResponseTitle(response, "Not Found");
    }

    @Test
    @DisplayName("Create book")
    void createBook() {

        Book expected = BookTestData.valid();

        Response response = booksClient.create(expected);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create book - verify persistence after retrieval")
    void createBookVerifyPersistence() {

        Book expected = BookTestData.valid();

        Response response = booksClient.create(expected);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertEquals(expected, actual);

        Response getResponse = booksClient.getById(expected.id());

        assertStatusCode(getResponse, 200);

        Book savedBook = getResponse.as(Book.class);

        assertEquals(expected, savedBook);
    }

    @Test
    @DisplayName("Create book with string id")
    void createBookWithStringId() {

        String invalidBody = """
                {
                    "id": "test",
                    "title": "testing",
                    "description": "Test description",
                    "pageCount": 2,
                    "excerpt": "Test excerpt",
                    "publishDate": "2025-01-15T10:00:00Z"
                }
                """;

        Response response = booksClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Create book with large id")
    void createBookWithLargeId() {

        String invalidBody = """
                {
                    "id": 2147883648,
                    "title": "testing",
                    "description": "Test description",
                    "pageCount": 2
                    "excerpt": "Test excerpt",
                    "publishDate": "2025-02-12T10:00:00Z"
                }
                """;

        Response response = booksClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Create book with string page count")
    void createBookWithStringPageCount() {

        String invalidBody = """
                {
                    "id": 1,
                    "title": "testing",
                    "description": "Test description",
                    "pageCount": "test",
                    "excerpt": "Test excerpt",
                    "publishDate": "2025-02-13T10:00:00Z"
                }
                """;

        Response response = booksClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Create book with large page count")
    void createBookWithLargePageCount() {

        String invalidBody = """
                {
                    "id": 1,
                    "title": "testing",
                    "description": "Test description",
                    "pageCount": 2147883648,
                    "excerpt": "Test excerpt",
                    "publishDate": "2025-02-11T10:00:00Z"
                }
                """;

        Response response = booksClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Create book with malformed payload")
    void createBookWithMalformedPayload() {

        String invalidBody = """
                {
                    "id": 1,
                    "title": "testing"
                    "description": "Test description",
                    "pageCount": 25,
                    "excerpt": "Test excerpt",
                    "publishDate": "2025-02-15T10:00:00Z"
                }
                """;

        Response response = booksClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Create book with nullable fields")
    void createBookWithNullableFields() {

        String body = """
            {
                "id": 1,
                "title": null,
                "description": null,
                "pageCount": 25,
                "excerpt": null,
                "publishDate": "2025-02-11T10:00:00Z"
            }
            """;

        Response response = booksClient.create(body);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertNull(actual.title());
        assertNull(actual.description());
        assertNull(actual.excerpt());
    }

    @Test
    @DisplayName("Update book")
    void updateBook() {

        int bookId = 1;

        Book expected = BookTestData.updated(bookId);

        Response response =
                booksClient.update(bookId, expected);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Create book with invalid publish date")
    void createBookWithInvalidPublishDate() {

        String body = """
            {
                "id": 1,
                "title": "testing",
                "description": "Test description",
                "pageCount": 20,
                "excerpt": "Test excerpt",
                "publishDate": "date"
            }
            """;

        Response response = booksClient.create(body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book - Verify persistence")
    void updateBookVerifyPersistence() {

        int bookId = 1;

        Book expected = BookTestData.updated(bookId);

        Response response =
                booksClient.update(bookId, expected);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertEquals(expected, actual);

        Response getResponse = booksClient.getById(bookId);

        assertStatusCode(getResponse, 200);

        Book savedBook = getResponse.as(Book.class);

        assertEquals(expected, savedBook);
    }

    @Test
    @DisplayName("Update book with string id")
    void updateBookWithStringId() {

        int bookId = 1;

        String body = """
                {
                    "id": "test",
                    "title": "Updated esting",
                    "description": "Updated description",
                    "pageCount": 200,
                    "excerpt": "Updated excerpt",
                    "publishDate": "2025-02-12T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book with large id")
    void updateBookWithLargeId() {

        int bookId = 1;

        String body = """
                {
                    "id": 2147883648,
                    "title": "Updated Testing",
                    "description": "Updated description",
                    "pageCount": 30,
                    "excerpt": "Updated excerpt",
                    "publishDate": "2025-02-12T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book with string page count")
    void updateBookWithStringPageCount() {

        int bookId = 1;

        String body = """
                {
                    "id": 1,
                    "title": "Updated Testing",
                    "description": "Updated description",
                    "pageCount": "test",
                    "excerpt": "Updated excerpt",
                    "publishDate": "2025-02-13T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book with large page count")
    void updateBookWithLargePageCount() {

        int bookId = 1;

        String body = """
                {
                    "id": 1,
                    "title": "Updated Testing",
                    "description": "Updated description",
                    "pageCount": 2147883648,
                    "excerpt": "Updated excerpt",
                    "publishDate": "2025-02-13T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book that does not exist")
    void updateBookThatDoesNotExist() {

        int bookId = 11111111;

        Book book = BookTestData.updated(bookId);

        Response response =
                booksClient.update(bookId, book);

        assertStatusCode(response, 404);
    }

    @Test
    @DisplayName("Update book with unknown fields")
    void updateBookWithUnknownFields() {

        int bookId = 1;

        String body = """
                {
                    "id2": 1,
                    "tittle": "Updated Testing",
                    "descriptio": "Updated description",
                    "pageCout": 100,
                    "excerp": "Updated excerpt",
                    "publishDat": "2025-02-14T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);
    }

    @Test
    @DisplayName("Update book with malformed payload")
    void updateBookWithMalformedPayload() {

        int bookId = 1;

        String body = """
                {
                    "id": 1,
                    "title": "Updated Testing"
                    "description": "Updated description",
                    "pageCount": 100,
                    "excerpt": "Updated excerpt",
                    "publishDate": "2025-02-25T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Update book with nullable fields")
    void updateBookWithNullableFields() {

        int bookId = 1;

        String body = """
                {
                    "id": 1,
                    "title": null,
                    "description": null,
                    "pageCount": 250,
                    "excerpt": null,
                    "publishDate": "2025-02-15T10:00:00Z"
                }
                """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 200);

        Book actual = response.as(Book.class);

        assertNull(actual.title());
        assertNull(actual.description());
        assertNull(actual.excerpt());
    }

    @Test
    @DisplayName("Update book with invalid publish date")
    void updateBookWithInvalidPublishDate() {

        int bookId = 1;

        String body = """
            {
                "id": 1,
                "title": "Updated Testing",
                "description": "Updated description",
                "pageCount": 100,
                "excerpt": "Updated excerpt",
                "publishDate": "test"
            }
            """;

        Response response =
                booksClient.update(bookId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }

    @Test
    @DisplayName("Delete book")
    void deleteBook() {

        int bookId = 1;

        Response response = booksClient.delete(bookId);

        assertStatusCode(response, 200);
    }

    @Test
    @DisplayName("Delete book should not be retrievable")
    void deleteBookShouldNotBeRetrievable() {

        int bookId = 1;

        Response deleteResponse = booksClient.delete(bookId);

        assertStatusCode(deleteResponse, 200);

        Response getResponse = booksClient.getById(bookId);

        assertStatusCode(getResponse, 404);
    }

    @Test
    @DisplayName("Delete book that does not exist")
    void deleteBookThatDoesNotExist() {

        int bookId = -111;

        Response response = booksClient.delete(bookId);

        assertStatusCode(response, 404);
    }

    @Test
    @DisplayName("Delete book with null id")
    void deleteBookWithNullId() {

        Response response = booksClient.delete(null);

        assertStatusCode(response, 400);

        assertResponseTitle(
                response,
                "One or more validation errors occurred."
        );
    }
}