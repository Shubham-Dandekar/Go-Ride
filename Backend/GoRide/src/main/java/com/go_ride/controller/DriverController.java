package com.go_ride.controller;

import com.go_ride.model.*;
import com.go_ride.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/go_ride/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping("/")
    public ResponseEntity<UserSession> addNewDriver(@Valid @RequestBody Driver driver) {
        return new ResponseEntity<>(driverService.addNewDriver(driver), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteExistingDriver(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(driverService.deleteExistingDriver(uuid), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Driver> getDetails(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(driverService.getDetails(uuid), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/password")
    public ResponseEntity<String> updatePassword(@PathVariable("uuid") String uuid, @Valid @RequestBody PasswordDTO passwordDTO) {
        return new ResponseEntity<>(driverService.updatePassword(uuid, passwordDTO), HttpStatus.OK);
    }

//    @PatchMapping("/{uuid}/update/email/{email}")
//    public ResponseEntity<String> updateEmail(@PathVariable("uuid") String uuid, @PathVariable("email") String newEmail) {
//        return new ResponseEntity<>(driverService.updateEmail(uuid, newEmail), HttpStatus.OK);
//    }

    @PatchMapping("/{uuid}/update/address/")
    public ResponseEntity<String> updateAddress(@PathVariable("uuid") String uuid, @RequestParam("city") String city,
                                                @RequestParam("state") String state, @RequestParam("pin-code") String pinCode) {
        return new ResponseEntity<>(driverService.updateAddress(uuid, new Address(city, state, pinCode)), HttpStatus.OK);
    }

    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) {
        return new ResponseEntity<>(driverService.forgotPassword(email), HttpStatus.OK);
    }

    @GetMapping("/verify/{uuid}")
    public ResponseEntity<String> sendVerificationOtpMail(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(driverService.sendVerificationOtpMail(uuid), HttpStatus.OK);
    }

    @PatchMapping("/verify/{uuid}/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable("uuid") String uuid, @PathVariable("otp") Integer otp) {
        return new ResponseEntity<>(driverService.verifyEmail(uuid, otp), HttpStatus.OK);
    }
}
