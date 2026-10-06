package com.example.bookstore.data;


public record Book(Integer id, String title, String description, Integer pageCount,
                   String excerpt, String publishDate) {
    public static Book valid(int id) {
        return new Book(id, "The API Automation Handbook", "A test book", 250,
                "A practical excerpt for API automation tests.", "2025-02-15T10:00:00Z");
    }

    public static Book updated(int id) {
        return new Book(id, "The API Automation Handbook - Updated", "Updated description", 300,
                "Updated excerpt.", "2025-02-15T10:00:00Z");
    }
}
