package com.example.bookstore.client;

import com.example.bookstore.data.Book;
import io.restassured.response.Response;

public class BooksClient {

    private static final String RESOURCE = "/Books";

    private final ApiClient apiClient;

    public BooksClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Response getAll() {
        return apiClient.get(RESOURCE);
    }

    public Response getById(int id) {
        return apiClient.get(RESOURCE + "/" + id);
    }

    public Response create(Book book) {
        return apiClient.post(RESOURCE, book);
    }

    public Response create(String body) {return apiClient.post(RESOURCE, body);}

    public Response update(int id, Book book) {return apiClient.put(RESOURCE + "/" + id, book);}

    public Response update(int id, String body) {return apiClient.put(RESOURCE + "/" + id, body);}

    public Response delete(Object id) { return apiClient.delete(RESOURCE + "/" + id);}
}