package com.go_ride.repository;

import com.go_ride.model.Admin;
import com.go_ride.model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @Query("select a.email from Admin a")
    public  List<String> getEmails();

    @Query("select new com.go_ride.model.UserDTO(a.email, a.name, a.address, a.emailVerified, a.role) " +
            "from Admin a where a.email = ?1")
    Optional<UserDTO> findDTOById(String email);
}
