package com.go_ride.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Validation {
    @Id
    private String email;

    @Column(unique = true)
    private Integer otp;

    private LocalDateTime sentTime;
}
