package com.example.bookstore.data;

public record Author(Integer id, Integer idBook, String firstName, String lastName) {
    public static Author valid(int id) {
        return new Author(id, 1, "Test", "Author");
    }

    public static Author updated(int id) {
        return new Author(id, 2, "Updated", "Author");
    }
}
