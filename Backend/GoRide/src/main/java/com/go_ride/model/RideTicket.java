package com.go_ride.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideTicket {
    private Integer id;

    @NotNull(message = "Boarding location should not be null.")
    private String boardingLocation;

    @NotNull(message = "Destination location should not be null.")
    private String destinationLocation;

    @NotNull(message = "Boarding date should not be null.")
    private String boardingDateTime;

    @Min(value = 1, message = "Minimum number of passenger should be 1.")
    private Integer noOfPassengers;

    @NotNull(message = "Distance should not be null.")
    @Min(value = 10, message = "Minimum distance should be 10Km")
    private Double distanceInKm;

    private Status status;

    private String customer;

    private String driver;

    private String driverLicenceNo;

    private String vehicleRegNo;

    private Double bill;

    public RideTicket(RideDTO rideDTO) {
        this.boardingLocation = rideDTO.getBoardingLocation();
        this.destinationLocation = rideDTO.getDestinationLocation();
        this.noOfPassengers = rideDTO.getNoOfPassengers();
        this.distanceInKm = rideDTO.getDistanceInKm();
    }

    public RideTicket(Ride ride) {
        this.boardingLocation = ride.getBoardingLocation();
        this.destinationLocation = ride.getDestinationLocation();
        this.noOfPassengers = ride.getNoOfPassengers();
        this.distanceInKm = ride.getDistanceInKm();
        this.id = ride.getId();
        this.driver = ride.getDriver().getName();
        this.driverLicenceNo = ride.getDriver().getLicenceNo();
        this.bill = ride.getBill();
        this.vehicleRegNo = ride.getVehicle().getRegistrationNo();
        this.status = ride.getStatus();
    }
}
