package com.go_ride.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be blank.")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String oldPassword;

    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be blank.")
    @Size(min = 8, message = "Password should be minimum of 8 characters")
    private String newPassword;
}
