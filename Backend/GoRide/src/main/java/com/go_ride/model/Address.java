package com.go_ride.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotNull(message = "City should not be null.")
    @NotBlank(message = "City should not be blank.")
    private String city;

    @NotNull(message = "State should not be null.")
    @NotBlank(message = "State should not be blank.")
    private String state;

    @NotNull(message = "Pin code should not be null.")
    @NotBlank(message = "Pin code should not be blank.")
    @Size(min = 6, max = 6, message = "Pin Code should be of 6 digits.")
    private String pinCode;
}
