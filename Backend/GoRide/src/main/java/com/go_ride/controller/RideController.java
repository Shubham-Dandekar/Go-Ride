package com.go_ride.controller;

import com.go_ride.model.Ride;
import com.go_ride.model.RideDTO;
import com.go_ride.model.RideTicket;
import com.go_ride.service.RideService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/go_ride/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    @PostMapping("/{uuid}")
    public ResponseEntity<RideTicket> addNewRide(@PathVariable("uuid") String uuid, @Valid @RequestBody RideDTO rideDTO) {
        return new ResponseEntity<>(rideService.addNewRide(uuid, rideDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{uuid}/{rideId}")
    public ResponseEntity<String> cancelRide(@PathVariable("uuid") String uuid, @PathVariable("rideId") Integer rideId) {
        return new ResponseEntity<>(rideService.cancelRide(uuid, rideId), HttpStatus.OK);
    }

    @GetMapping("/admins/customers/{uuid}")
    public ResponseEntity<List<Ride>> ViewAllRidesByCustomer(@PathVariable("uuid") String uuid, @RequestParam("email") String email) {
        return new ResponseEntity<>(rideService.ViewAllRidesByCustomer(uuid, email), HttpStatus.OK);
    }

    @GetMapping("/customers/{uuid}")
    public ResponseEntity<List<RideTicket>> ViewAllRidesByCustomer(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(rideService.ViewAllRidesByCustomer(uuid), HttpStatus.OK);
    }

    @GetMapping("/admins/drivers/{uuid}")
    public ResponseEntity<List<Ride>> ViewAllRidesByDriver(@PathVariable("uuid") String uuid, @RequestParam("email") String email) {
        return new ResponseEntity<>(rideService.ViewAllRidesByDriver(uuid, email), HttpStatus.OK);
    }

    @GetMapping("/drivers/{uuid}")
    public ResponseEntity<List<RideTicket>> ViewAllRidesByDriver(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(rideService.ViewAllRidesByDriver(uuid), HttpStatus.OK);
    }

    @GetMapping("/all/{uuid}")
    public ResponseEntity<List<RideTicket>> getAllRides(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(rideService.getAllRides(uuid), HttpStatus.OK);
    }

//    @GetMapping("/date/{uuid}")
//    public ResponseEntity<List<Ride>> getRidesByDate(@PathVariable("uuid") String uuid, @RequestParam("date") LocalDateTime dateTime) {
//        return new ResponseEntity<>(rideService.getRidesByDate(uuid, dateTime), HttpStatus.OK);
//    }
//
//    @GetMapping("/betweenDates/{uuid}")
//    public ResponseEntity<List<Ride>> getAllRidesBetweenDays(@PathVariable("uuid") String uuid, @RequestParam("startDate") LocalDateTime startDateTime, @RequestParam("endDate") LocalDateTime endDateTime) {
//        return new ResponseEntity<>(rideService.getAllRidesBetweenDays(uuid, startDateTime, endDateTime), HttpStatus.OK);
//    }

    @GetMapping("/vehicle/{uuid}/{registrationNo}")
    public ResponseEntity<List<Ride>> getRidesByVehicle(@PathVariable("uuid") String uuid, @PathVariable("registrationNo") String registrationNo) {
        return new ResponseEntity<>(rideService.getRidesByVehicle(uuid, registrationNo), HttpStatus.OK);
    }

    @PatchMapping("/complete/{uuid}/{rideId}")
    public ResponseEntity<String> completeRide(@PathVariable("uuid") String uuid, @PathVariable("rideId") Integer rideId) {
        return new ResponseEntity<>(rideService.completeRide(uuid, rideId), HttpStatus.OK);
    }
}
