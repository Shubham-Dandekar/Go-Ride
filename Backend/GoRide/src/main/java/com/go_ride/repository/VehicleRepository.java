package com.go_ride.repository;

import com.go_ride.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByVehicleType(String vehicleType);

    List<Vehicle> findByAvailable(Boolean available);

    Integer countByVehicleType(String vehicleType);

    @Query("select COUNT(v) from Vehicle v")
    Integer countAll();
}
