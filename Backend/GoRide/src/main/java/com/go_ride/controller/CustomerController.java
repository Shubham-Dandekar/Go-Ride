package com.go_ride.controller;

import com.go_ride.model.*;
import com.go_ride.service.CustomerService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/go_ride/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<UserSession> addNewCustomer(@Valid @RequestBody Customer customer) throws MessagingException, UnsupportedEncodingException {
        return new ResponseEntity<>(customerService.addNewCustomer(customer), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteExistingCustomer(@PathVariable("uuid") String uuid) throws MessagingException {
        return new ResponseEntity<>(customerService.deleteExistingCustomer(uuid), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Customer> getDetails(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(customerService.getDetails(uuid), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/password")
    public ResponseEntity<String> updatePassword(@PathVariable("uuid") String uuid, @Valid @RequestBody PasswordDTO passwordDTO) {
        return new ResponseEntity<>(customerService.updatePassword(uuid, passwordDTO), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/email/{email}")
    public ResponseEntity<String> updateEmail(@PathVariable("uuid") String uuid, @PathVariable("email") String newEmail) {
        return new ResponseEntity<>(customerService.updateEmail(uuid, newEmail), HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/update/address/")
    public ResponseEntity<String> updateAddress(@PathVariable("uuid") String uuid, @RequestParam("city") String city,
                                                @RequestParam("state") String state, @RequestParam("pin-code") String pinCode) {
        return new ResponseEntity<>(customerService.updateAddress(uuid, new Address(city, state, pinCode)), HttpStatus.OK);
    }

    @GetMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable("email") String email) throws MessagingException {
        return new ResponseEntity<>(customerService.forgotPassword(email), HttpStatus.OK);
    }

    @GetMapping("/verify/{uuid}")
    public ResponseEntity<String> sendVerificationOtpMail(@PathVariable("uuid") String uuid) throws MessagingException {
        return new ResponseEntity<>(customerService.sendVerificationOtpMail(uuid), HttpStatus.OK);
    }

    @PatchMapping("/verify/{uuid}/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable("uuid") String uuid, @PathVariable("otp") Integer otp) {
        return new ResponseEntity<>(customerService.verifyEmail(uuid, otp), HttpStatus.OK);
    }
}
