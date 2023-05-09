package com.go_ride.service;

public interface ValidationService {
    Boolean verifyEmail(String email, Integer otp);

    Integer getVerificationOTP(String email);

    void deleteOTP(String email);
}
