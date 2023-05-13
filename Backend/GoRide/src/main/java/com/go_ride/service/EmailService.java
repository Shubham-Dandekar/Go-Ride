package com.go_ride.service;

import com.go_ride.model.Ride;
import org.springframework.mail.MailException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendWelcomeEmail(String emailAddress, String name);

    void sendAccountDeletionEmail(String emailAddress, String name);

    void sendForgotPasswordEmail(String emailAddress, String name, String password);

    void sendVerificationEmail(String emailAddress, Integer otp);

    void sendNewRideEmail(String emailAddress, String name,  Ride ride);

    void sendRideCancellationEmail(String emailAddress, String name, Ride ride);
}
