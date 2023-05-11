package com.go_ride.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {
    @Id
    @NotNull(message = "Registration number of vehicle should not be null.")
    @Size(min = 10, max = 10, message = "Registration number of vehicle should be of 10 alpha-numeric value")
    private String registrationNo;

    @NotNull(message = "Vehicle type should not be null.")
    private String vehicleType;

    @Min(value = 1, message = "Vehicle should have at least 1 seat.")
    private Integer seats;

    @NotNull(message = "Per Km Rate should not be null.")
    @Min(value = 1, message = "Per Km Rate at least ₹1")
    private Double perKmRate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean available;
}
