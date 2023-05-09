package com.go_ride.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotNull(message = "Boarding location should not be null.")
    private String boardingLocation;

    @NotNull(message = "Destination location should not be null.")
    private String destinationLocation;

    @NotNull(message = "Boarding date should not be null.")
    private LocalDateTime boardingDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime completedDateTime;

    @Min(value = 1, message = "Minimum number of passenger should be 1.")
    private Integer noOfPassengers;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String customerName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Vehicle vehicle;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @NotNull(message = "Distance should not be null.")
    @Min(value = 10, message = "Minimum distance should be 10Km")
    private Double distanceInKm;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double bill;

    public Ride(RideDTO rideDTO) {
        this.boardingLocation = rideDTO.getBoardingLocation();
        this.destinationLocation = rideDTO.getDestinationLocation();
        this.boardingDateTime = rideDTO.getBoardingDateTime();
        this.noOfPassengers = rideDTO.getNoOfPassengers();
        this.distanceInKm = rideDTO.getDistanceInKm();
    }
}
