package com.go_ride.controller;

import com.go_ride.model.*;
import com.go_ride.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/go_ride/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/")
    public ResponseEntity<UserSession> addNewAdmin(@Valid @RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.addNewAdmin(admin), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteExistingAdmin(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(adminService.deleteExistingAdmin(uuid), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Admin> getDetails(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(adminService.getDetails(uuid), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/password")
    public ResponseEntity<String> updatePassword(@PathVariable("uuid") String uuid, @Valid @RequestBody PasswordDTO passwordDTO) {
        return new ResponseEntity<>(adminService.updatePassword(uuid, passwordDTO), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/email/{email}")
    public ResponseEntity<String> updateEmail(@PathVariable("uuid") String uuid, @PathVariable("email") String newEmail) {
        return new ResponseEntity<>(adminService.updateEmail(uuid, newEmail), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/address/")
    public ResponseEntity<String> updateAddress(@PathVariable("uuid") String uuid, @RequestParam("city") String city,
                                                @RequestParam("state") String state, @RequestParam("pin-code") String pinCode) {
        return new ResponseEntity<>(adminService.updateAddress(uuid, new Address(city, state, pinCode)), HttpStatus.OK);
    }

    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) {
        return new ResponseEntity<>(adminService.forgotPassword(email), HttpStatus.OK);
    }

    @GetMapping("/verify/{uuid}")
    public ResponseEntity<String> sendVerificationOtpMail(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(adminService.sendVerificationOtpMail(uuid), HttpStatus.OK);
    }

    @PatchMapping("/verify/{uuid}/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable("uuid") String uuid, @PathVariable("otp") Integer otp) {
        return new ResponseEntity<>(adminService.verifyEmail(uuid, otp), HttpStatus.OK);
    }
}
