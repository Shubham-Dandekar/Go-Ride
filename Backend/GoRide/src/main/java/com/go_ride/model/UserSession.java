package com.go_ride.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserSession {
    @Id
    private String uuid;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String email;

    private String userLogInDateTime;
}
