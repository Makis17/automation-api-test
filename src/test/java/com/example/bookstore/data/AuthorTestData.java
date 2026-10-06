package com.example.bookstore.data;

import com.example.bookstore.util.RandomUtils;

public final class AuthorTestData {

    private AuthorTestData() {
    }

    public static Author valid() {
        return valid(RandomUtils.randomInt(1, 10_000));
    }

    public static Author valid(int id) {
        return new Author(
                id,
                RandomUtils.randomInt(1, 200),
                RandomUtils.randomString("FirstName - ",8),
                RandomUtils.randomString("lastName - ",8)
        );
    }

    public static Author updated(int id) {
        return new Author(
                id,
                RandomUtils.randomInt(1, 200),
                RandomUtils.randomString("FirstName - ",4),
                RandomUtils.randomString("lastName - ",5)
        );
    }
}