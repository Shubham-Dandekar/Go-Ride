package com.go_ride.service;

import com.go_ride.model.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle addNewVehicle(String uuid, Vehicle vehicle);

    Vehicle updateVehicle(String uuid, Vehicle vehicle);

    List<Vehicle> viewVehiclesOfType(String uuid, String vehicleType);

    List<Vehicle> viewAvailableVehicle(String uuid);

    String removeVehicle(String uuid, String vehicleRegistrationNo);

    Integer countVehiclesByType(String uuid, String vehicleType);

    Integer countAllVehicles(String uuid);
}
