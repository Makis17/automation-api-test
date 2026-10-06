package com.example.bookstore.client;

import com.example.bookstore.data.Author;
import io.restassured.response.Response;

public class AuthorsClient {
    private static final String RESOURCE = "/api/v1/Authors";
    private final ApiClient apiClient;

    public AuthorsClient(ApiClient apiClient) { this.apiClient = apiClient; }

    public Response getAll() { return apiClient.get(RESOURCE); }
    public Response getById(int id) { return apiClient.get(RESOURCE + "/" + id); }
    public Response create(Author author) { return apiClient.post(RESOURCE, author); }
    public Response update(int id, Author author) { return apiClient.put(RESOURCE + "/" + id, author); }
    public Response delete(int id) { return apiClient.delete(RESOURCE + "/" + id); }
}
