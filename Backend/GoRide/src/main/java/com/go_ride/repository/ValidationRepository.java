package com.go_ride.repository;

import com.go_ride.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, String> {

    @Query("select v.otp from Validation v")
    List<Integer> getAllOTPs();
}
