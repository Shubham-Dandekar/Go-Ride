package com.go_ride.service;

import com.go_ride.exception.CredentialsException;
import com.go_ride.model.Ride;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendWelcomeEmail(String emailAddress, String name) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            String message = "Dear " + name + "\nThank you for joining the Go Ride community! " +
                    "We are excited to have you on board and look forward to providing you with exceptional" +
                    " service.\nThank you for choosing Go Ride. We hope you enjoy your experience with us." +
                    "\nBest regards,\nGo Ride Team";

            helper.setText(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendAccountDeletionEmail(String emailAddress, String name) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            String message = "Dear " + name + "\nYour account with email: " + emailAddress +
                    " has been successfully deleted. \nBest regards,\nGo Ride Team";

            helper.setText(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendForgotPasswordEmail(String emailAddress, String name, String password) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            String message =  "Dear " + name + "\nYour password has been reset successfully.\n" +
                    "Use new password to login into your account.\nNew password: " + password +
                    "\nBest regards,\nGo Ride Team";

            helper.setText(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendVerificationEmail(String emailAddress, Integer otp) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            String message =  "Dear " + emailAddress + "\nThank you for choosing Go Ride. To verify your email, an " +
                    "one-time password (OTP) given below.\n OTP: " + otp + "\nBest regards,\nGo Ride Team";

            helper.setText(message);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendNewRideEmail(String emailAddress, String name, Ride ride) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            StringBuilder message = new StringBuilder("Dear " + name + "\n");
            message.append("Your ride has booked been successfully. " +
                    "For more details check your account.\n");
            message.append("Boarding Location: " + ride.getBoardingLocation());
            message.append("\nDestination Location: " + ride.getDestinationLocation());
            message.append("\nBoarding Date & Time: " + ride.getBoardingDateTime());
            message.append("\nNote: Time is in 24 hour format.");
            message.append("\nHappy journey with Go Ride.\n");
            message.append("Best regards,\nGo Ride Team");

            helper.setText(message.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendRideCancellationEmail(String emailAddress, String name, Ride ride) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");

        try {
            String fromName = "Go Ride Team";
            String fromEmail = "shubhamdandekar9@gmail.com";
            InternetAddress fromAddress = new InternetAddress(fromEmail, fromName);
            helper.setFrom(fromAddress);
            helper.setTo(emailAddress);
            helper.setSubject("Welcome to Go Ride");

            StringBuilder message = new StringBuilder("Dear " + name + "\n");
            message.append("Your ride has been cancelled successfully." +
                    "For more details check your account.\n");
            message.append("Boarding Location: " + ride.getBoardingLocation());
            message.append("\nDestination Location: " + ride.getDestinationLocation());
            message.append("\nBoarding Date & Time: " + ride.getBoardingDateTime());
            message.append("\nNote: Time is in 24 hour format.\n");
            message.append("Best regards,\nGo Ride Team");

            helper.setText(message.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }
}
