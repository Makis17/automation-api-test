package com.example.bookstore.config;

public final class ApiConfig {

    private static final String DEFAULT_BASE_URL =
            "https://fakerestapi.azurewebsites.net";

    private static final String DEFAULT_API_VERSION = "/api/v1";

    private ApiConfig() {
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", DEFAULT_BASE_URL)
                .replaceAll("/$", "");
    }

    public static String apiVersion() {
        return System.getProperty("apiVersion", DEFAULT_API_VERSION);
    }

    public static String apiBasePath() {
        return baseUrl() + apiVersion();
    }
}