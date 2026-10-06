package com.example.bookstore.data;

public record Author(
        Integer id,
        Integer idBook,
        String firstName,
        String lastName
) {
}