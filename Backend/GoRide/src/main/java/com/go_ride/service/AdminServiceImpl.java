package com.go_ride.service;

import com.go_ride.exception.AdminException;
import com.go_ride.exception.CredentialsException;
import com.go_ride.exception.UserSessionException;
import com.go_ride.model.*;
import com.go_ride.repository.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepo;

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
    public UserSession addNewAdmin(Admin admin) {
        List<String> emails = checkEmailService.getAllRegisteredEmails();

        if(emails.contains(admin.getEmail()))
            throw new CredentialsException("User with email: " + admin.getEmail() + " already registered.");

        admin.setEmailVerified(Verify.NOT_VERIFIED);
        adminRepo.save(admin);

        emailService.sendWelcomeEmail(admin.getEmail(), admin.getName());

        LogInDTO logInDTO = new LogInDTO(admin.getEmail(), admin.getPassword(), Role.ADMIN);

        return logInLogOutService.logInUser(logInDTO);
    }

    @Override
    public String deleteExistingAdmin(String uuid) {
        UserSession adminSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(adminSession.getEmail())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        adminRepo.delete(admin);
        List<UserSession> sessions = userSessionRepo.findByEmail(admin.getEmail());
        userSessionRepo.deleteAll(sessions);
        emailService.sendAccountDeletionEmail(admin.getEmail(), admin.getName());

        return "Admin deleted with email: " + adminSession.getEmail();
    }

    @Override
    public Admin getDetails(String uuid) {
        UserSession adminSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        return adminRepo.findById(adminSession.getEmail())
                .orElseThrow(() -> new AdminException("Admin not found."));
    }

    @Override
    public String updatePassword(String uuid, PasswordDTO passwordDTO) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        checkEmailService.checkAdminCredentials(admin.getEmail(), passwordDTO.getOldPassword());
        List<UserSession> sessions = userSessionRepo.findByEmail(admin.getEmail());
        userSessionRepo.deleteAll(sessions);
        admin.setPassword(passwordDTO.getNewPassword());
        adminRepo.save(admin);

        return "Admin password changed successfully and " + logInLogOutService.logOutUser(uuid);
    }

    @Override
    public String updateEmail(String uuid, String newEmail) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        List<String > emails = checkEmailService.getAllRegisteredEmails();

        if(emails.contains(newEmail)) throw new CredentialsException("Email already exists.");
        List<UserSession> sessions = userSessionRepo.findByEmail(admin.getEmail());
        userSessionRepo.deleteAll(sessions);
        adminRepo.delete(admin);

        admin.setEmail(newEmail);
        admin.setEmailVerified(Verify.NOT_VERIFIED);

        adminRepo.save(admin);

        return "Admin email changed successfully.";
    }

    @Override
    public String updateAddress(String uuid, Address address) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        admin.setAddress(address);
        adminRepo.save(admin);

        return "Admin address changed successfully.";
    }

    @Override
    public String forgotPassword(String email) {
        Admin admin = adminRepo.findById(email).orElseThrow(() -> new AdminException("User not found."));

        String password = RandomString.make(16);
        emailService.sendForgotPasswordEmail(email, admin.getName(), password);
        List<UserSession> sessions = userSessionRepo.findByEmail(email);
        if(sessions.size() != 0) {
            userSessionRepo.deleteAll(sessions);
        }
        admin.setPassword(password);
        adminRepo.save(admin);

        return "User password reset successfully. You have been logged out from all sessions.";
    }

    @Override
    public String sendVerificationOtpMail(String uuid) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        Integer otp = validationService.getVerificationOTP(admin.getEmail());

        emailService.sendVerificationEmail(admin.getEmail(), otp);

        return "Email sent successfully.";
    }

    @Override
    public String verifyEmail(String uuid, Integer otp) {
        UserSession session = userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        Admin admin = adminRepo.findById(session.getEmail()).orElseThrow(() -> new AdminException("User not found."));

        if(validationService.verifyEmail(admin.getEmail(), otp)) {
            validationService.deleteOTP(admin.getEmail());
            admin.setEmailVerified(Verify.VERIFIED);
            adminRepo.save(admin);
            return "Email verified successfully.";
        }

        return "Wrong otp entered.";
    }
}
