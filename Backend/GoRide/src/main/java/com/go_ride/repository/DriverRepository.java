package com.go_ride.repository;

import com.go_ride.model.Driver;
import com.go_ride.model.Ride;
import com.go_ride.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

    @Query("select d.email from Driver d")
    public List<String> getEmails();

    @Query("select new com.go_ride.model.UserDTO(d.email, d.name, d.address, d.emailVerified, d.role) " +
            "from Driver d where d.email = ?1")
    Optional<UserDTO> findDTOById(String email);

    @Query("from Driver d where d.available = ?1")
    public List<Driver> findAllAvailableDrivers(Boolean available);
}
