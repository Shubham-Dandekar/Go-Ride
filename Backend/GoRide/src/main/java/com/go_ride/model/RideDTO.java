package com.go_ride.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    @NotNull(message = "Boarding location should not be null.")
    private String boardingLocation;

    @NotNull(message = "Destination location should not be null.")
    private String destinationLocation;

    @NotNull(message = "Boarding date should not be null.")
    private LocalDateTime boardingDateTime;

    @Min(value = 1, message = "Minimum number of passenger should be 1.")
    private Integer noOfPassengers;

    @NotNull(message = "Distance should not be null.")
    @Min(value = 10, message = "Minimum distance should be 10Km")
    private Double distanceInKm;

    @NotNull(message = "Registration number of vehicle should not be null.")
    @Size(min = 10, max = 10, message = "Registration number of vehicle should be of 10 alpha-numeric value")
    private String vehicleRegNo;
}
