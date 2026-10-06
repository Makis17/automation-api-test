package com.example.bookstore.util;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    private RandomUtils() {
    }

    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException(
                    "min cannot be greater than max"
            );
        }

        return ThreadLocalRandom.current()
                .nextInt(min, max + 1);
    }

}
