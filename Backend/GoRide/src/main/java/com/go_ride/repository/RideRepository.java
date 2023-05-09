package com.go_ride.repository;

import com.go_ride.model.Driver;
import com.go_ride.model.Ride;
import com.go_ride.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {

    @Query("select c.rides from Customer c where c.email = ?1")
    List<Ride> findRidesByCustomer(String email);

    List<Ride> findByDriver(Driver driver);

    List<Ride> findByVehicle(Vehicle vehicle);

    List<Ride> findByBoardingDateTime(LocalDateTime dateTime);

    List<Ride> findByBoardingDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
