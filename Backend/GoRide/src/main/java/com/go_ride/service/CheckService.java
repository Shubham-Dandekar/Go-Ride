package com.go_ride.service;

import java.util.List;

public interface CheckService {
    List<String> getAllRegisteredEmails();

    void checkAdminCredentials(String email, String password);

    void checkCustomerCredentials(String email, String password);

    void checkDriverCredentials(String email, String password);
}
