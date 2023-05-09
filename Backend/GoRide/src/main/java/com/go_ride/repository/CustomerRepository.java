package com.go_ride.repository;

import com.go_ride.model.Customer;
import com.go_ride.model.Ride;
import com.go_ride.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("select c.email from Customer c")
    public List<String> getEmails();

    @Query("select new com.go_ride.model.UserDTO(c.email, c.name, c.address, c.emailVerified, c.role) " +
            "from Customer c where c.email = ?1")
    Optional<UserDTO> findDTOById(String email);
}
