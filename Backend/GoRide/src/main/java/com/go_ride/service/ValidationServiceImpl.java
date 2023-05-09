package com.go_ride.service;

import com.go_ride.exception.ValidationException;
import com.go_ride.model.Validation;
import com.go_ride.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ValidationServiceImpl implements ValidationService{
    @Autowired
    private ValidationRepository validationRepo;

    @Override
    public Boolean verifyEmail(String email, Integer otp) {
        Validation validation = validationRepo.findById(email).get();
        LocalDateTime today = LocalDateTime.now();
        Duration duration = Duration.between(validation.getSentTime(), today);


        if(duration.getSeconds() > 300) {
            validationRepo.delete(validation);
            return false;
        }

        return validation.getOtp().equals(otp);
    }

    @Override
    public Integer getVerificationOTP(String email) {
        List<Validation> validations = validationRepo.findAll();

        validations.stream().filter((valid) -> {
            LocalDateTime today = LocalDateTime.now();
            Duration duration = Duration.between(valid.getSentTime(), today);

            return duration.getSeconds() > 300;
        }).toList();

        validationRepo.deleteAll(validations);

        List<Integer> otpList = validationRepo.getAllOTPs();
        Integer otp = generateOTP();

        while(otpList.contains(otp)) {
            otp = generateOTP();
        }

        Optional<Validation> optionalValidation = validationRepo.findById(email);

        if(optionalValidation.isPresent()) {
            validationRepo.delete(optionalValidation.get());
        }

        Validation validation = new Validation();

        validation.setEmail(email);
        validation.setOtp(otp);
        validation.setSentTime(LocalDateTime.now());

        validationRepo.save(validation);

        return otp;
    }

    @Override
    public void deleteOTP(String email) {
        Validation validation = validationRepo.findById(email)
                .orElseThrow(() -> new ValidationException("Otp not present for email: " + email));

        validationRepo.delete(validation);
    }

    public Integer generateOTP() {
        Random random = new Random();
        return random.nextInt(100000, 999999);
    }
}
