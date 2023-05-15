package com.go_ride.service;

import com.go_ride.exception.AdminException;
import com.go_ride.exception.UserSessionException;
import com.go_ride.exception.VehicleException;
import com.go_ride.model.Role;
import com.go_ride.model.UserSession;
import com.go_ride.model.Vehicle;
import com.go_ride.repository.UserSessionRepository;
import com.go_ride.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{
    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private UserSessionRepository userSessionRepo;

    @Override
    public Vehicle addNewVehicle(String uuid, Vehicle vehicle) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");
        vehicle.setAvailable(true);
        vehicle.setVehicleType(vehicle.getVehicleType().toUpperCase());
        vehicle.setRegistrationNo(vehicle.getRegistrationNo().toUpperCase());
        return vehicleRepo.save(vehicle);
    }

    @Override
    public Vehicle updatePerKmRate(String uuid, String registrationNo, Double perKmRate) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");

        Vehicle existingVehicle = vehicleRepo.findById(registrationNo)
                .orElseThrow(() -> new VehicleException("Vehicle not found with registration no: " + registrationNo));

        existingVehicle.setPerKmRate(perKmRate);
        existingVehicle.setAvailable(true);

        return vehicleRepo.save(existingVehicle);
    }

    @Override
    public List<Vehicle> viewVehiclesOfType(String uuid, String vehicleType) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");

        return vehicleRepo.findByVehicleType(vehicleType);
    }

    @Override
    public List<Vehicle> viewAvailableVehicle(String uuid) {
        userSessionRepo.findById(uuid).orElseThrow(() -> new UserSessionException("User not logged in."));

        return vehicleRepo.findByAvailable(true);
    }

    @Override
    public String removeVehicle(String uuid, String vehicleRegistrationNo) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");

        Vehicle existingVehicle = vehicleRepo.findById(vehicleRegistrationNo)
                .orElseThrow(() -> new VehicleException("Vehicle not found with registration no: " + vehicleRegistrationNo));

        if(!existingVehicle.getAvailable())
            throw new VehicleException("Vehicle currently booked for a ride. Hence unable to delete at this moment.");

        vehicleRepo.delete(existingVehicle);

        return "Vehicle removed successfully.";
    }

    @Override
    public Integer countVehiclesByType(String uuid, String vehicleType) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");

        return vehicleRepo.countByVehicleType(vehicleType);
    }

    @Override
    public List<Vehicle> getAllVehicles(String uuid) {
        UserSession userSession = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(userSession.getRole() != Role.ADMIN) new AdminException("You are not admin.");

        return vehicleRepo.findAll();
    }
}
