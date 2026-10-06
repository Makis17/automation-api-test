package com.example.bookstore.tests;

import com.example.bookstore.client.ApiClient;
import com.example.bookstore.client.AuthorsClient;
import com.example.bookstore.client.BooksClient;
import com.example.bookstore.client.RestAssuredApiClient;
import com.example.bookstore.config.ApiConfig;
import org.junit.jupiter.api.BeforeEach;

public  abstract class BaseApiTest {

    protected AuthorsClient authorsClient;
    protected BooksClient booksClient;

    @BeforeEach
    void setUpApiClients() {

        ApiClient apiClient =
                new RestAssuredApiClient(ApiConfig.apiBasePath());

        authorsClient = new AuthorsClient(apiClient);
        booksClient = new BooksClient(apiClient);
    }
}
