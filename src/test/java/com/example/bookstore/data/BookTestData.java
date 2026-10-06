package com.example.bookstore.data;

import com.example.bookstore.util.RandomUtils;

public final class BookTestData {

    private BookTestData() {
    }

    public static Book valid() {
        return valid(RandomUtils.randomInt(1, 10_000));
    }

    public static Book valid(int id) {
        return new Book(
                id,
                RandomUtils.randomString("Tittle - ",8),
                RandomUtils.randomString("Description - ",15),
                250,
                "A practical excerpt for API automation tests.",
                "2025-02-15T10:00:00Z"
        );
    }

    public static Book updated(int id) {
        return new Book(
                id,
                RandomUtils.randomString("updated title - ",8),
                RandomUtils.randomString("updated - ",15),
                300,
                "Updated excerpt.",
                "2025-02-15T10:00:00Z"
        );
    }
}