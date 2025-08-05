package com.neptunesoftware.venusWs.Utils;

public class ApiException extends RuntimeException {
    private final String statusCode;
    private final String responseBody;

    public ApiException(String statusCode, String responseBody) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

}