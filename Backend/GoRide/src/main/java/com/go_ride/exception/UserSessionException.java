package com.go_ride.exception;

public class UserSessionException extends RuntimeException{
    public UserSessionException() {
    }

    public UserSessionException(String message) {
        super(message);
    }
}
