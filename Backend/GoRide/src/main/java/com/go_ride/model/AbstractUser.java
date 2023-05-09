package com.go_ride.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Embeddable
public abstract class AbstractUser {
    @Id
    @Email
    private String email;

    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be blank.")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be blank.")
    private String name;

    @Embedded
    private Address address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(value = EnumType.STRING)
    private Verify emailVerified;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}

