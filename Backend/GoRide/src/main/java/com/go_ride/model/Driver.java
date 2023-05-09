package com.go_ride.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class Driver extends AbstractUser{
    @NotNull(message = "Licence no should not be null.")
    @Column(unique = true)
    private String licenceNo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean available;
}
