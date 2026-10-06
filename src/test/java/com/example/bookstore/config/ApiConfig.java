package com.example.bookstore.config;

public final class ApiConfig {
    private static final String DEFAULT_BASE_URL = "https://fakerestapi.azurewebsites.net";

    private ApiConfig() { }

    public static String baseUrl() {
        return System.getProperty("baseUrl", DEFAULT_BASE_URL).replaceAll("/$", "");
    }

    public static String apiVersion() {
        return "/api/v1";
    }
}
