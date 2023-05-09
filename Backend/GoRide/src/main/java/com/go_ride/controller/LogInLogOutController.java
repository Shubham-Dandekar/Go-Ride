package com.go_ride.controller;

import com.go_ride.model.LogInDTO;
import com.go_ride.model.UserSession;
import com.go_ride.service.LogInLogOutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/go_ride")
public class LogInLogOutController {
    @Autowired
    private LogInLogOutService logInLogOutService;

    @PostMapping("/logIn")
    public ResponseEntity<UserSession> logInUser(@Valid @RequestBody LogInDTO logInDTO) {
        return new ResponseEntity<>(logInLogOutService.logInUser(logInDTO), HttpStatus.OK);
    }

    @GetMapping("/logout/{uuid}")
    public ResponseEntity<String> logOutUser(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(logInLogOutService.logOutUser(uuid), HttpStatus.OK);
    }
}
