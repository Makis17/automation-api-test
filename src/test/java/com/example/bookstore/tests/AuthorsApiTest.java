package com.example.bookstore.tests;

import com.example.bookstore.data.Author;
import com.example.bookstore.data.AuthorTestData;
import com.example.bookstore.util.ReportExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static com.example.bookstore.util.ResponseAssertions.assertStatusCode;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Authors API - Bonus")
@ExtendWith(ReportExtension.class)
class AuthorsApiTest extends BaseApiTest {

    @Test
    @DisplayName("Retrieval of all authors")
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
    @DisplayName("create author")
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
    @DisplayName("Retrieval author by id")
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
    @DisplayName("Delete author")
    void deleteAuthor() {

        int authorId = 1;

        Response response = authorsClient.delete(authorId);

        assertStatusCode(response, 200);
    }

}
