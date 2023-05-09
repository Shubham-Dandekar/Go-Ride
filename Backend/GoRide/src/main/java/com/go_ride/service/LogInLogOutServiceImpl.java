package com.go_ride.service;

import com.go_ride.exception.CredentialsException;
import com.go_ride.exception.LogOutException;
import com.go_ride.model.*;
import com.go_ride.repository.AdminRepository;
import com.go_ride.repository.CustomerRepository;
import com.go_ride.repository.DriverRepository;
import com.go_ride.repository.UserSessionRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LogInLogOutServiceImpl  implements LogInLogOutService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private DriverRepository driverRepo;

    @Autowired
    private UserSessionRepository userSessionRepo;

    @Autowired
    private CheckService checkService;

    @Override
    public UserSession logInUser(LogInDTO logInDTO) {
        if(!(logInDTO.getRole() == Role.ADMIN || logInDTO.getRole() == Role.CUSTOMER || logInDTO.getRole() == Role.DRIVER))
            throw new CredentialsException("Invalid role type.");

        switch (logInDTO.getRole()) {
            case ADMIN -> {
                checkService.checkAdminCredentials(logInDTO.getEmail(), logInDTO.getPassword());
                return saveUserSession(logInDTO.getEmail(), Role.ADMIN);
            }
            case CUSTOMER -> {
                checkService.checkCustomerCredentials(logInDTO.getEmail(), logInDTO.getPassword());
                return saveUserSession(logInDTO.getEmail(), Role.CUSTOMER);
            }
            default -> {
                checkService.checkDriverCredentials(logInDTO.getEmail(), logInDTO.getPassword());
                return saveUserSession(logInDTO.getEmail(), Role.DRIVER);
            }
        }
    }

    public String getUuid() {
        StringBuilder key = new StringBuilder(RandomString.make(6));

        key.append(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss").format(LocalDateTime.now()));

        return key.toString();
    }

    public UserSession saveUserSession(String email, Role role) {
        UserSession session = new UserSession();

        session.setEmail(email);
        session.setRole(role);
        session.setUuid(getUuid());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        session.setUserLogInDateTime(LocalDateTime.now().format(formatter));

        return userSessionRepo.save(session);
    }

    @Override
    public String logOutUser(String uuid) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new LogOutException("User already logged out."));
        userSessionRepo.delete(userSession);
        return "User logged out successfully.";
    }
}
