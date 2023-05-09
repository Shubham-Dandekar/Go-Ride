package com.go_ride.exception;

import jakarta.persistence.RollbackException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* --------------------------------------   Validation Exception    ----------------------------------------------*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> validationExceptionHandler(MethodArgumentNotValidException validationException, WebRequest request) {

        ErrorDetails err = new ErrorDetails();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        err.setTimestamp(LocalDateTime.now().format(formatter));
        err.setDetails("Validation Error...");
        err.setMessage(validationException.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    /*----------------------------------------  NoHandlerFoundException  --------------------------------------*/
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException noHandlerFoundException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), noHandlerFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /*----------------------------------------  RollbackException  --------------------------------------*/
    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<ErrorDetails> rollbackException(RollbackException rollbackException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err=new ErrorDetails(LocalDateTime.now().format(formatter), rollbackException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    /*--------------------------------------------  Null Pointer Exception  --------------------------------------------------*/
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDetails> nullPointerExceptionHandler(NullPointerException exception, WebRequest webRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /*--------------------------------------------  Exception  --------------------------------------------------*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(Exception exception, WebRequest webRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   Admin Exception    ----------------------------------------------*/
    @ExceptionHandler(AdminException.class)
    public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminException adminException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), adminException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   Customer Exception    ----------------------------------------------*/
    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException customerException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), customerException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   Admin Exception    ----------------------------------------------*/
    @ExceptionHandler(DriverException.class)
    public ResponseEntity<ErrorDetails> driverExceptionHandler(DriverException driverException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), driverException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   Ride Exception    ----------------------------------------------*/
    @ExceptionHandler(RideException.class)
    public ResponseEntity<ErrorDetails> rideExceptionHandler(RideException rideException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), rideException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /*--------------------------------------------  Vehicle Exception  --------------------------------------------------*/
    @ExceptionHandler(VehicleException.class)
    public ResponseEntity<ErrorDetails> vehicleExceptionHandler(VehicleException exception, WebRequest webRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now().format(formatter), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   LogIn Exception    ----------------------------------------------*/
    @ExceptionHandler(LogInException.class)
    public ResponseEntity<ErrorDetails> logInExceptionHandler(LogInException logInException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), logInException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   LogIn Exception    ----------------------------------------------*/
    @ExceptionHandler(LogOutException.class)
    public ResponseEntity<ErrorDetails> logOutExceptionHandler(LogOutException logOutException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), logOutException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   UserSession Exception    ----------------------------------------------*/
    @ExceptionHandler(CredentialsException.class)
    public ResponseEntity<ErrorDetails> credentialsExceptionHandler(CredentialsException credentialsException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), credentialsException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   UserSession Exception    ----------------------------------------------*/
    @ExceptionHandler(UserSessionException.class)
    public ResponseEntity<ErrorDetails> userSessionExceptionHandler(UserSessionException sessionException, WebRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ErrorDetails err = new ErrorDetails(LocalDateTime.now().format(formatter), sessionException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}


