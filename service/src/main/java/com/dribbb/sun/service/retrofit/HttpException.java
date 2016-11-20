package com.dribbb.sun.service.retrofit;

public class HttpException extends RuntimeException {
    public int code;
    public String message;

    public HttpException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}