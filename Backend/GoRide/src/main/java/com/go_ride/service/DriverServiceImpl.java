package com.go_ride.service;

import com.go_ride.exception.*;

import com.go_ride.model.*;
import com.go_ride.repository.DriverRepository;
import com.go_ride.repository.UserSessionRepository;
import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService{
    @Autowired
    private DriverRepository driverRepo;

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
    public UserSession addNewDriver(Driver driver) throws MessagingException, UnsupportedEncodingException {
        List<String> emails = checkEmailService.getAllRegisteredEmails();

        if(!emails.isEmpty() && emails.contains(driver.getEmail()))
            throw new CredentialsException("User with email: " + driver.getEmail() + " already registered.");

        driver.setAvailable(true);
        driver.setEmailVerified(Verify.NOT_VERIFIED);
        driverRepo.save(driver);

        emailService.sendWelcomeEmail(driver.getEmail(), driver.getName());

        LogInDTO logInDTO = new LogInDTO(driver.getEmail(), driver.getPassword(), Role.DRIVER);

        return logInLogOutService.logInUser(logInDTO);
    }

    @Override
    public String deleteExistingDriver(String uuid) throws MessagingException {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(userSession.getEmail())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        List<UserSession> sessions = userSessionRepo.findByEmail(driver.getEmail());
        userSessionRepo.deleteAll(sessions);
        driverRepo.delete(driver);

        emailService.sendAccountDeletionEmail(driver.getEmail(), driver.getName());

        return "Driver deleted with email: " + userSession.getEmail();
    }

    @Override
    public Driver getDetails(String uuid) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        return driverRepo.findById(session.getEmail())
                .orElseThrow(() -> new CustomerException("Customer not found."));
    }

    @Override
    public String updatePassword(String uuid, PasswordDTO passwordDTO) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail()).orElseThrow(() -> new CustomerException("User not found."));

        checkEmailService.checkDriverCredentials(driver.getEmail(), passwordDTO.getOldPassword());
        List<UserSession> sessions = userSessionRepo.findByEmail(driver.getEmail());
        userSessionRepo.deleteAll(sessions);
        driver.setPassword(passwordDTO.getNewPassword());
        driverRepo.save(driver);

        return "Driver password changed successfully";
    }

    @Override
    public String updateEmail(String uuid, String newEmail) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        if(driver.getEmailVerified() == Verify.VERIFIED) throw new CredentialsException("Email cannot be changed once verified.");

        List<String > emails = checkEmailService.getAllRegisteredEmails();

        if(emails.contains(newEmail)) throw new CredentialsException("Email already exists.");
        List<UserSession> sessions = userSessionRepo.findByEmail(driver.getEmail());
        userSessionRepo.deleteAll(sessions);
        driverRepo.delete(driver);

        driver.setEmail(newEmail);
        driver.setEmailVerified(Verify.NOT_VERIFIED);

        driverRepo.save(driver);

        return "Driver email changed successfully.";
    }

    @Override
    public String updateAddress(String uuid, Address address) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        driver.setAddress(address);
        driverRepo.save(driver);

        return "Driver address changed successfully.";
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {
        Driver driver = driverRepo.findById(email).orElseThrow(() -> new AdminException("User not found."));

        String password = RandomString.make(16);
        emailService.sendForgotPasswordEmail(email, driver.getName(), password);
        List<UserSession> sessions = userSessionRepo.findByEmail(email);
        if(sessions.size() != 0) {
            userSessionRepo.deleteAll(sessions);
        }
        driver.setPassword(password);
        driverRepo.save(driver);

        return "User password reset successfully. You have been logged out from all sessions.";
    }

    @Override
    public String sendVerificationOtpMail(String uuid) throws MessagingException {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail())
                .orElseThrow(() -> new AdminException("User not found."));

        Integer otp = validationService.getVerificationOTP(driver.getEmail());

        emailService.sendVerificationEmail(driver.getEmail(), otp);

        return "Email sent successfully.";
    }

    @Override
    public String verifyEmail(String uuid, Integer otp) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail())
                .orElseThrow(() -> new AdminException("User not found."));

        if(validationService.verifyEmail(driver.getEmail(), otp)) {
            driver.setEmailVerified(Verify.VERIFIED);
            validationService.deleteOTP(driver.getEmail());
            driverRepo.save(driver);
            return "Email verified successfully.";
        }
        throw new ValidationException("Wrong otp entered.");
    }
}
