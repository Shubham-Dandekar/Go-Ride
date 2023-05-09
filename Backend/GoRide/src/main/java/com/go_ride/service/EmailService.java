package com.go_ride.service;

public interface EmailService {
    void sendWelcomeEmail(String emailAddress, String name);

    void sendAccountDeletionEmail(String emailAddress, String name);

    void sendForgotPasswordEmail(String emailAddress, String name, String password);

    void sendVerificationEmail(String emailAddress, Integer otp);

    void sendNewRideEmail(String emailAddress, String name);

    void sendRideCancellationEmail(String emailAddress, String name);
}
