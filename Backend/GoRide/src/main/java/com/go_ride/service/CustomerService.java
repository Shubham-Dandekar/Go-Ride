package com.go_ride.service;

import com.go_ride.model.*;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface CustomerService {
    UserSession addNewCustomer(Customer customer) throws MessagingException, UnsupportedEncodingException;

    String deleteExistingCustomer(String uuid) throws MessagingException;

    Customer getDetails(String uuid);

    String updatePassword(String uuid, PasswordDTO passwordDTO);

    String updateEmail(String uuid, String newEmail);

    String updateAddress(String uuid, Address address);

    String forgotPassword(String email) throws MessagingException;

    String sendVerificationOtpMail(String uuid) throws MessagingException;

    String verifyEmail(String uuid, Integer otp);
}
