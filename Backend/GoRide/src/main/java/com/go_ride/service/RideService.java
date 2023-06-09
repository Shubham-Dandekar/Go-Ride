package com.go_ride.service;

import com.go_ride.model.Ride;
import com.go_ride.model.RideDTO;
import com.go_ride.model.RideTicket;
import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RideService {
    RideTicket addNewRide(String uuid, RideDTO rideDTO) throws MessagingException;

    String cancelRide(String uuid, Integer rideId) throws MessagingException;

    List<Ride> ViewAllRidesByCustomer(String uuid, String email);

    List<RideTicket> ViewAllRidesByCustomer(String uuid);

    List<Ride> ViewAllRidesByDriver(String uuid, String email);

    List<RideTicket> ViewAllRidesByDriver(String uuid);

    List<RideTicket> getAllRides(String uuid);

    List<Ride> getRidesByDate(String uuid, LocalDateTime dateTime);

    List<Ride> getAllRidesBetweenDays(String uuid, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Ride> getRidesByVehicle(String uuid, String registrationNo);

    String completeRide(String uuid, Integer rideId);
}
