package com.go_ride.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;

    private String name;

    private Address address;

    private Boolean emailVerified;

    private Role role;
}
