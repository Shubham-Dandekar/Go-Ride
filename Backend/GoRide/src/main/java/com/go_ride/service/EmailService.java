package com.go_ride.service;

import com.go_ride.model.Ride;
import jakarta.mail.MessagingException;
import org.springframework.mail.MailException;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendWelcomeEmail(String emailAddress, String name) throws UnsupportedEncodingException, MessagingException;

    void sendAccountDeletionEmail(String emailAddress, String name) throws MessagingException;

    void sendForgotPasswordEmail(String emailAddress, String name, String password) throws MessagingException;

    void sendVerificationEmail(String emailAddress, Integer otp) throws MessagingException;

    void sendNewRideEmail(String emailAddress, String name,  Ride ride) throws MessagingException;

    void sendRideCancellationEmail(String emailAddress, String name, Ride ride) throws MessagingException;
}
