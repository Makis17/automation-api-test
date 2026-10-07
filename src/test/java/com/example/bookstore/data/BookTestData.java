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
                RandomUtils.randomString("Title - ",8),
                RandomUtils.randomString("Description - ",15),
                RandomUtils.randomInt(2,10),
                RandomUtils.randomString("excerpt - ",15),
                "2025-02-11T10:00:00Z"
        );
    }

    public static Book updated(int id) {
        return new Book(
                id,
                RandomUtils.randomString("updated title - ",8),
                RandomUtils.randomString("updated - ",15),
                RandomUtils.randomInt(2,10),
                RandomUtils.randomString(" update excerpt - ",15),
                "2025-02-11T10:00:00Z"
        );
    }
}