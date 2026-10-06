package com.example.bookstore.data;

import java.time.OffsetDateTime;

public record Book(Integer id, String title, String description, Integer pageCount,
                   String excerpt, OffsetDateTime publishDate) {
    public static Book valid(int id) {
        return new Book(id, "The API Automation Handbook", "A test book", 250,
                "A practical excerpt for API automation tests.", OffsetDateTime.parse("2025-01-15T10:00:00Z"));
    }

    public static Book updated(int id) {
        return new Book(id, "The API Automation Handbook - Updated", "Updated description", 300,
                "Updated excerpt.", OffsetDateTime.parse("2025-02-15T10:00:00Z"));
    }
}
