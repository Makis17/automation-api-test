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
    public static String randomString(String prefix, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder(prefix);

        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current()
                    .nextInt(characters.length());

            result.append(characters.charAt(index));
        }

        return result.toString();
    }

}
