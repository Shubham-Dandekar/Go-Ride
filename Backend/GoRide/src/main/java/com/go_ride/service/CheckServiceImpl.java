package com.go_ride.service;

import com.go_ride.exception.CredentialsException;
import com.go_ride.model.Admin;
import com.go_ride.model.Customer;
import com.go_ride.model.Driver;
import com.go_ride.repository.AdminRepository;
import com.go_ride.repository.CustomerRepository;
import com.go_ride.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {
    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private DriverRepository driverRepo;

    @Override
    public List<String> getAllRegisteredEmails() {
        List<String> emails = new ArrayList<>();

        emails.addAll(adminRepo.getEmails());
        emails.addAll(customerRepo.getEmails());
        emails.addAll(driverRepo.getEmails());

        return emails;
    }

    @Override
    public void checkAdminCredentials(String email, String password) {
        Optional<Admin> admin = adminRepo.findById(email);

        if (admin.isEmpty()) {
            throw new CredentialsException("Admin not registered with username: " + email);
        }

        if (!admin.get().getPassword().equals(password)) {
            throw new CredentialsException("Incorrect password entered.");
        }
    }

    @Override
    public void checkCustomerCredentials(String email, String password) {
        Optional<Customer> customer = customerRepo.findById(email);

        if (customer.isEmpty()) {
            throw new CredentialsException("Customer not registered with username: " + email);
        }

        if (!customer.get().getPassword().equals(password)) {
            throw new CredentialsException("Incorrect password entered.");
        }
    }

    @Override
    public void checkDriverCredentials(String email, String password) {
        Optional<Driver> driver = driverRepo.findById(email);

        if (driver.isEmpty()) {
            throw new CredentialsException("Driver not registered with username: " + email);
        }

        if (!driver.get().getPassword().equals(password)) {
            throw new CredentialsException("Incorrect password entered.");
        }
    }
}
