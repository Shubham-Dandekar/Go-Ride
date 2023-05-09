package com.go_ride.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInDTO {
    @Email
    private String email;

    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be blank.")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
