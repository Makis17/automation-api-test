package com.example.bookstore.tests;

import com.example.bookstore.data.Author;
import com.example.bookstore.data.AuthorTestData;
import com.example.bookstore.util.ReportExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static com.example.bookstore.util.ResponseAssertions.assertResponseTitle;
import static com.example.bookstore.util.ResponseAssertions.assertStatusCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Authors API - Bonus")
@ExtendWith(ReportExtension.class)
class AuthorsApiTest extends BaseApiTest {

    @Test
    @DisplayName("Retrieve all authors")
    void getAllAuthors() {

        Response response = authorsClient.getAll();

        assertStatusCode(response, 200);
    }

    @Test
    @DisplayName("Retrieve author by id")
    void getAuthorById() {

        int authorId = 1;

        Response response = authorsClient.getById(authorId);

        assertStatusCode(response, 200);
        assertEquals(
                authorId,
                response.jsonPath().getInt("id")
        );
    }

    @Test
    @DisplayName("Retrieve author by id that does not exists")
    void getAuthorByIdInvalid() {

        int authorId = -1;

        Response response = authorsClient.getById(authorId);

        assertStatusCode(response, 404);
        assertResponseTitle(response, "Not Found");

    }

    @Test
    @DisplayName("Create author")
    void createAuthor() {

        Author author = AuthorTestData.valid();

        Response response = authorsClient.create(author);

        assertStatusCode(response, 200);

        Author actual = response.as(Author.class);

        assertEquals(author.id(), actual.id());

        assertEquals(author.idBook(), actual.idBook());

        assertEquals(author.firstName(), actual.firstName());

        assertEquals(author.lastName(), actual.lastName());
    }

    @Test
    @DisplayName("Create author - verify persistence after retrieval")
    void createAuthorVerifyPersistence() {

        Author expected = AuthorTestData.valid();

        Response response = authorsClient.create(expected);

        assertStatusCode(response, 200);

        Author actual = response.as(Author.class);

        assertEquals(expected, actual);

        Response getResponse = authorsClient.getById(expected.id());

        assertStatusCode(getResponse, 200);

        Author savedAuthor = getResponse.as(Author.class);

        assertEquals(expected, savedAuthor);

    }

    @Test
    @DisplayName("Create author with string id")
    void createAuthorWithStringId() {

        String invalidBody = """
                {
                    "id": "invalid-id",
                    "idBook": 1,
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Create author with malformed payload")
    void createAuthorWithMalformedPayload() {

        String invalidBody = """
                {
                    "id": 1
                    "idBook": 1,
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Create author with large id")
    void createAuthorWithLargeId() {


        String invalidBody = """
                {
                    "id": "2147883648",
                    "idBook": 1,
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Create author with string id")
    void createAuthorWithStringIdBook() {

        String invalidBody = """
                {
                    "id": 1,
                    "idBook": "test,
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Create author without firstName & lastname")
    void createAuthorWithoutNames() {

        String invalidBody = """
                {
                  "id": "abc",
                  "idBook": 1,
                  "firstName": null,
                  "lastName": null
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Create author with large id book")
    void createAuthorWithSLargeIdBook() {

        String invalidBody = """
                {
                    "id": 1,
                    "idBook": 2147883648,
                    "firstName": "John",
                    "lastName": "Smith"
                }
                """;

        Response response = authorsClient.create(invalidBody);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Update author")
    void updateAuthor() {

        int authorId = 1;

        Author expected = AuthorTestData.updated(authorId);

        Response response =
                authorsClient.update(authorId, expected);

        assertStatusCode(response, 200);

        Author actual = response.as(Author.class);

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Update author - Verify persistence")
    void updateAuthorVerifyPersistence() {

        int authorId = 1;

        Author expected = AuthorTestData.updated(authorId);

        Response response =
                authorsClient.update(authorId, expected);

        assertStatusCode(response, 200);

        Author actual = response.as(Author.class);

        assertEquals(expected, actual);

        Response getResponse = authorsClient.getById(authorId);

        assertStatusCode(getResponse, 200);

        Author savedAuthor = getResponse.as(Author.class);

        assertEquals(expected, savedAuthor);

    }


    @Test
    @DisplayName("Update author with string id")
    void updateAuthorWithStringId() {

        int authorId = 1;

        String body = """
                {
                  "id": "test",
                  "idBook": 1,
                  "firstName": "John",
                  "lastName": "Smith"
                }
                """;

        Response response =
                authorsClient.update(authorId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");
    }

    @Test
    @DisplayName("Update author with large id")
    void updateAuthorWithLargeId() {

        int authorId = 1;

        String body = """
                {
                  "id": 2147883648,
                  "idBook": 1,
                  "firstName": "John",
                  "lastName": "Smith"
                }
                """;

        Response response =
                authorsClient.update(authorId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");
    }

    @Test
    @DisplayName("Update author with header id that does not exists")
    void updateAuthorWithNotExistingAuthor() {

        int authorId = 11111111;

        String body = """
                {
                  "id": 2,
                  "idBook": 1,
                  "firstName": "John",
                  "lastName": "Smith"
                }
                """;

        Response response =
                authorsClient.update(authorId, body);

        assertStatusCode(response, 404);

    }

    @Test
    @DisplayName("Update author with unknown fields")
    void updateAuthorWithUnknownFields() {

        int authorId = 11;

        String body = """
                {
                  "id2": 2,
                  "idBoo3k": 1,
                  "firstame": "John",
                  "lasName": "Smith"
                }
                """;

        Response response =
                authorsClient.update(authorId, body);

        assertStatusCode(response, 400);

    }

    @Test
    @DisplayName("Update author with malformed payload")
    void updateAuthorWithMalformedPayload() {

        int authorId = 11;

        String body = """
                {
                  "id": 2,
                  "idBook": 1,
                  "firstName": "John"
                  "lasName": "Smith"
                }
                """;

        Response response =
                authorsClient.update(authorId, body);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");

    }

    @Test
    @DisplayName("Delete author")
    void deleteAuthor() {

        int authorId = 1;

        Response response = authorsClient.delete(authorId);

        assertStatusCode(response, 200);
    }

    @Test
    @DisplayName("Delete author should not be retrievable")
    void deleteAuthorNotBeRetrievable() {

        int authorId = 1;

        Response deleteResponse = authorsClient.delete(authorId);

        assertStatusCode(deleteResponse, 200);

        Response getResponse = authorsClient.getById(authorId);

        assertStatusCode(getResponse, 404);
    }


    @Test
    @DisplayName("Delete author that does not exists")
    void deleteAuthorThatDoesNotExists() {

        int authorId = -111;

        Response response = authorsClient.delete(authorId);

        assertStatusCode(response, 404);
    }

    @Test
    @DisplayName("Delete author with null Id")
    void deleteAuthorWithNullId() {

        Response response = authorsClient.delete(null);

        assertStatusCode(response, 400);

        assertResponseTitle(response, "One or more validation errors occurred.");
    }

}
