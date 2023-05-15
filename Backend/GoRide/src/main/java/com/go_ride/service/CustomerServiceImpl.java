package com.go_ride.service;

import com.go_ride.exception.*;
import com.go_ride.model.*;
import com.go_ride.repository.CustomerRepository;
import com.go_ride.repository.UserSessionRepository;
import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserSessionRepository userSessionRepo;

    @Autowired
    private LogInLogOutService logInLogOutService;

    @Autowired
    private CheckService checkEmailService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserSession addNewCustomer(Customer customer) throws MessagingException, UnsupportedEncodingException {
        List<String> emails = checkEmailService.getAllRegisteredEmails();

        if(!emails.isEmpty() && emails.contains(customer.getEmail()))
            throw new CredentialsException("User with email: " + customer.getEmail() + " already registered.");

        customer.setEmailVerified(Verify.NOT_VERIFIED);
        customerRepo.save(customer);

        emailService.sendWelcomeEmail(customer.getEmail(), customer.getName());

        LogInDTO logInDTO = new LogInDTO(customer.getEmail(), customer.getPassword(), Role.CUSTOMER);

        return logInLogOutService.logInUser(logInDTO);
    }

    @Override
    public String deleteExistingCustomer(String uuid) throws MessagingException {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(userSession.getEmail())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        List<UserSession> sessions = userSessionRepo.findByEmail(customer.getEmail());
        userSessionRepo.deleteAll(sessions);
        customerRepo.delete(customer);

        emailService.sendAccountDeletionEmail(customer.getEmail(), customer.getName());

        return "Customer deleted with email: " + userSession.getEmail();
    }

    @Override
    public Customer getDetails(String uuid) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        return customerRepo.findById(session.getEmail())
                .orElseThrow(() -> new CustomerException("Customer not found."));
    }

    @Override
    public String updatePassword(String uuid, PasswordDTO passwordDTO) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail()).orElseThrow(() -> new CustomerException("User not found."));

        checkEmailService.checkCustomerCredentials(customer.getEmail(), passwordDTO.getOldPassword());
        List<UserSession> sessions = userSessionRepo.findByEmail(customer.getEmail());
        userSessionRepo.deleteAll(sessions);
        customer.setPassword(passwordDTO.getNewPassword());
        customerRepo.save(customer);

        return "Customer password changed successfully";
    }

    @Override
    public String updateEmail(String uuid, String newEmail) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        if(customer.getEmailVerified() == Verify.VERIFIED) throw new CredentialsException("Email cannot be changed once verified.");

        List<String > emails = checkEmailService.getAllRegisteredEmails();

        if(emails.contains(newEmail)) throw new CredentialsException("Email already exists.");
        List<UserSession> sessions = userSessionRepo.findByEmail(customer.getEmail());
        userSessionRepo.deleteAll(sessions);
        customerRepo.delete(customer);

        customer.setEmail(newEmail);
        customer.setEmailVerified(Verify.NOT_VERIFIED);

        customerRepo.save(customer);

        return "Customer email changed successfully.";
    }

    @Override
    public String updateAddress(String uuid, Address address) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        customer.setAddress(address);
        customerRepo.save(customer);

        return "Customer address changed successfully.";
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {
        Customer customer = customerRepo.findById(email).orElseThrow(() -> new AdminException("User not found."));

        String password = RandomString.make(16);
        emailService.sendForgotPasswordEmail(email, customer.getName(), password);
        List<UserSession> sessions = userSessionRepo.findByEmail(email);
        if(sessions.size() != 0) {
            userSessionRepo.deleteAll(sessions);
        }
        customer.setPassword(password);
        customerRepo.save(customer);

        return "User password reset successfully. You have been logged out from all sessions.";
    }

    @Override
    public String sendVerificationOtpMail(String uuid) throws MessagingException {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail())
                .orElseThrow(() -> new AdminException("User not found."));

        Integer otp = validationService.getVerificationOTP(customer.getEmail());

        emailService.sendVerificationEmail(customer.getEmail(), otp);

        return "Email sent successfully.";
    }

    @Override
    public String verifyEmail(String uuid, Integer otp) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail())
                .orElseThrow(() -> new AdminException("User not found."));

        if(validationService.verifyEmail(customer.getEmail(), otp)) {
            customer.setEmailVerified(Verify.VERIFIED);
            validationService.deleteOTP(customer.getEmail());
            customerRepo.save(customer);
            return "Email verified successfully.";
        }

        throw new ValidationException("Wrong otp entered.");
    }
}
