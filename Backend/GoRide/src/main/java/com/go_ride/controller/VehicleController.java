package com.go_ride.controller;

import com.go_ride.model.Vehicle;
import com.go_ride.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/go_ride/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/{uuid}")
    public ResponseEntity<Vehicle> addNewVehicle(@PathVariable("uuid") String uuid, @Valid @RequestBody Vehicle vehicle) {
        return new ResponseEntity<>(vehicleService.addNewVehicle(uuid, vehicle), HttpStatus.CREATED);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable("uuid") String uuid, @RequestParam("vehicleRegistrationNo") String registrationNo, @RequestParam("perKmRate") Double perKmRate) {
        return new ResponseEntity<>(vehicleService.updatePerKmRate(uuid, registrationNo, perKmRate), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<List<Vehicle>> viewVehiclesOfType(@PathVariable("uuid") String uuid, @RequestParam("vehicleType") String vehicleType) {
        return new ResponseEntity<>(vehicleService.viewVehiclesOfType(uuid, vehicleType), HttpStatus.OK);
    }

    @GetMapping("/available/{uuid}")
    public ResponseEntity<List<Vehicle>> viewAvailableVehicle(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(vehicleService.viewAvailableVehicle(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> removeVehicle(@PathVariable("uuid") String uuid, @RequestParam("vehicleRegistrationNo") String vehicleRegistrationNo) {
        return new ResponseEntity<>(vehicleService.removeVehicle(uuid, vehicleRegistrationNo), HttpStatus.OK);
    }

    @GetMapping("/count/{uuid}")
    public ResponseEntity<Integer> countVehiclesByType(@PathVariable("uuid") String uuid, @RequestParam("vehicleType") String vehicleType) {
        return new ResponseEntity<>(vehicleService.countVehiclesByType(uuid, vehicleType), HttpStatus.OK);
    }

    @GetMapping("/all/{uuid}")
    public ResponseEntity<List<Vehicle>> countAllVehicles(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(vehicleService.getAllVehicles(uuid), HttpStatus.OK);
    }
}
