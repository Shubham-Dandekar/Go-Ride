package com.go_ride.exception;

public class CredentialsException extends RuntimeException{
    public CredentialsException() {
    }

    public CredentialsException(String message) {
        super(message);
    }
}
