package com.go_ride.service;

import com.go_ride.exception.CredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendWelcomeEmail(String emailAddress, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Welcome to Go Ride");

        String message = "Dear " + name + "\nThank you for joining the Go Ride community! " +
                "We are excited to have you on board and look forward to providing you with exceptional" +
                " service.\nThank you for choosing Go Ride. We hope you enjoy your experience with us." +
                "Best regards,\nGo Ride Team";

        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendAccountDeletionEmail(String emailAddress, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Account deletion from Go Ride");

        String message = "Dear " + name + "\nYour account with email: " + emailAddress +
                " has been successfully deleted. \nBest regards,\nGo Ride Team";

        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendForgotPasswordEmail(String emailAddress, String name, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Welcome to Go Ride");

        String message = "Dear " + name + "\nYour password has been reset successfully.\n" +
                "Use new password to login into your account.\nNew password: " + password +
                "\nBest regards,\nGo Ride Team";

        mailMessage.setText(message);
        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            throw new CredentialsException("Unable to reset password at this moment. Try after some time.");
        }
    }

    @Override
    public void sendVerificationEmail(String emailAddress, Integer otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("One-Time Password (OTP) Verification for Go Ride");

        String message = "Dear " + emailAddress + "\nThank you for choosing Go Ride. To verify your email, an " +
                "one-time password (OTP) given below.\n OTP: " + otp + "\nBest regards,\nGo Ride Team";

        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendNewRideEmail(String emailAddress, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Ride booked in Go Ride");

        StringBuilder message = new StringBuilder("Dear " + name + "\n");
        message.append("Your ride has booked been successfully. " +
                "For more details check your account.\n");
        message.append("Best regards,\nGo Ride Team");

        mailMessage.setText(message.toString());

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendRideCancellationEmail(String emailAddress, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("shubhamdandekar9@gmail.com");
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject("Ride booked in Go Ride");

        StringBuilder message = new StringBuilder("Dear " + name + "\n");
        message.append("Your ride has been cancelled successfully." +
                "For more details check your account.\n");
        message.append("Best regards,\nGo Ride Team");

        mailMessage.setText(message.toString());

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }
}
