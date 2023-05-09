package com.go_ride.service;

import com.go_ride.model.*;

public interface AdminService {
    UserSession addNewAdmin(Admin admin);

    String deleteExistingAdmin(String uuid);

    Admin getDetails(String uuid);

    String updatePassword(String uuid, PasswordDTO passwordDTO);

    String updateEmail(String uuid, String newEmail);

    String updateAddress(String uuid, Address address);

    String forgotPassword(String email);

    String sendVerificationOtpMail(String uuid);

    String verifyEmail(String uuid, Integer otp);
}
