package com.go_ride.service;

import com.go_ride.model.*;

public interface CustomerService {
    UserSession addNewCustomer(Customer customer);

    String deleteExistingCustomer(String uuid);

    Customer getDetails(String uuid);

    String updatePassword(String uuid, PasswordDTO passwordDTO);

    String updateEmail(String uuid, String newEmail);

    String updateAddress(String uuid, Address address);

    String forgotPassword(String email);

    String sendVerificationOtpMail(String uuid);

    String verifyEmail(String uuid, Integer otp);
}
