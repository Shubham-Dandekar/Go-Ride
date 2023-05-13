package com.go_ride.service;

import com.go_ride.exception.*;
import com.go_ride.model.*;
import com.go_ride.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RideServiceImpl implements RideService{
    @Autowired
    private RideRepository rideRepo;

    @Autowired
    private DriverRepository driverRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserSessionRepository userSessionRepo;

    @Autowired
    private EmailService emailService;


    @Override
    public RideTicket addNewRide(String uuid, RideDTO rideDTO) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        List<Driver> drivers = driverRepo.findAllAvailableDrivers(true);

        Vehicle vehicle = vehicleRepo.findById(rideDTO.getVehicleRegNo())
                        .orElseThrow(() -> new VehicleException("No vehicle found for registration no: "
                        + rideDTO.getVehicleRegNo()));

        if(!vehicle.getAvailable()) throw  new VehicleException("Vehicle not available of registration no: "
                + rideDTO.getVehicleRegNo());

        if(vehicle.getSeats() < rideDTO.getNoOfPassengers())  throw  new VehicleException("No of passengers must not be " +
                "more than seats available for your selected vehicle!");

        if(drivers.isEmpty()) throw new DriverException("Driver is not available.");

        Random random = new Random();

        Integer num = random.nextInt(drivers.size());

        Driver driver = drivers.get(num);
        Ride ride = new Ride(rideDTO);

        ride.setDriver(driver);
        driver.setAvailable(false);

        ride.setVehicle(vehicle);
        vehicle.setAvailable(false);

        ride.setCustomerName(customer.getEmail());
        ride.setBill(vehicle.getPerKmRate() * ride.getDistanceInKm());
        ride.setStatus(Status.CONFIRMED);

        customer.getRides().add(ride);

        rideRepo.save(ride);
        driverRepo.save(driver);
        vehicleRepo.save(vehicle);
        customerRepo.save(customer);

        emailService.sendNewRideEmail(customer.getEmail(), customer.getName(), ride);

        RideTicket ticket = new RideTicket(rideDTO);
        ticket.setId(ride.getId());
        ticket.setCustomer(customer.getName());
        ticket.setDriver(ride.getDriver().getName());
        ticket.setDriverLicenceNo(ride.getDriver().getLicenceNo());
        ticket.setBill(ride.getBill());
        ticket.setVehicleRegNo(ride.getVehicle().getRegistrationNo());
        ticket.setStatus(ride.getStatus());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ticket.setBoardingDateTime(ride.getBoardingDateTime().format(formatter));

        return ticket;
    }

    @Override
    public String cancelRide(String uuid, Integer rideId) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Ride existingRide = rideRepo.findById(rideId)
                .orElseThrow(() -> new RideException("No ride found for id: " + rideId));

        Customer customer = customerRepo.findById(existingRide.getCustomerName())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        if(LocalDateTime.now().isAfter(existingRide.getBoardingDateTime()) ||
                LocalDateTime.now().isEqual(existingRide.getBoardingDateTime()))
            throw new RideException("Ride cannot be cancelled after boarding time");

        Vehicle vehicle = existingRide.getVehicle();
        vehicle.setAvailable(true);

        Driver driver = existingRide.getDriver();
        driver.setAvailable(true);

        existingRide.setStatus(Status.CANCELLED);
        existingRide.setCompletedDateTime(LocalDateTime.now());

        driverRepo.save(driver);
        vehicleRepo.save(vehicle);
        rideRepo.save(existingRide);

        emailService.sendRideCancellationEmail(customer.getEmail(), customer.getName(), existingRide);

        return "Ride cancelled successfully.";
    }

    @Override
    public List<Ride> ViewAllRidesByCustomer(String uuid, String email) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        Customer customer = customerRepo.findById(email)
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        return rideRepo.findRidesByCustomer(customer.getEmail());
    }

    @Override
    public List<RideTicket> ViewAllRidesByCustomer(String uuid) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Customer customer = customerRepo.findById(session.getEmail())
                .orElseThrow(() -> new CredentialsException("Invalid credentials."));

        List<Ride> rides = rideRepo.findRidesByCustomer(customer.getEmail());
        List<RideTicket> tickets = new ArrayList<>();

        for (Ride ride: rides) {
            RideTicket ticket = new RideTicket(ride);
            ticket.setCustomer(customer.getName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ticket.setBoardingDateTime(ride.getBoardingDateTime().format(formatter));

            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public List<Ride> ViewAllRidesByDriver(String uuid, String email) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        Driver driver = driverRepo.findById(email)
                .orElseThrow(() -> new DriverException("Driver not found with email: " + email));

        return rideRepo.findByDriver(driver);
    }

    @Override
    public List<RideTicket> ViewAllRidesByDriver(String uuid) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Driver driver = driverRepo.findById(session.getEmail())
                .orElseThrow(() -> new DriverException("Driver not found with email: " + session.getEmail()));

        List<Ride> rides = rideRepo.findByDriver(driver);
        List<RideTicket> tickets = new ArrayList<>();

        for (Ride ride: rides) {
            RideTicket ticket = new RideTicket(ride);
            ticket.setCustomer(ride.getCustomerName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ticket.setBoardingDateTime(ride.getBoardingDateTime().format(formatter));

            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public List<RideTicket> getAllRides(String uuid) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        List<Ride> rides = rideRepo.findAll();
        List<RideTicket> tickets = new ArrayList<>();

        for (Ride ride: rides) {
            RideTicket ticket = new RideTicket(ride);
            ticket.setCustomer(ride.getCustomerName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ticket.setBoardingDateTime(ride.getBoardingDateTime().format(formatter));

            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public List<Ride> getRidesByDate(String uuid, LocalDateTime dateTime) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        return rideRepo.findByBoardingDateTime(dateTime);
    }

    @Override
    public List<Ride> getAllRidesBetweenDays(String uuid, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        return rideRepo.findByBoardingDateTimeBetween(startDateTime, endDateTime);
    }

    @Override
    public List<Ride> getRidesByVehicle(String uuid, String registrationNo) {
        UserSession session = userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        if(session.getRole() != Role.ADMIN) throw new AdminException("You are not admin.");

        Vehicle vehicle = vehicleRepo.findById(registrationNo)
                .orElseThrow(() -> new VehicleException("Vehicle not found with registration no: " + registrationNo));

        return rideRepo.findByVehicle(vehicle);
    }

    @Override
    public String completeRide(String uuid, Integer rideId) {
        userSessionRepo.findById(uuid)
                .orElseThrow(() -> new UserSessionException("User not logged in."));

        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RideException("No ride found for id: " + rideId));

        if(LocalDateTime.now().isBefore(ride.getBoardingDateTime()) ||
                LocalDateTime.now().isEqual(ride.getBoardingDateTime()))
            throw new RideException("Ride cannot be completed before boarding time");

        Driver driver = ride.getDriver();
        driver.setAvailable(true);

        Vehicle vehicle = ride.getVehicle();
        vehicle.setAvailable(true);

        ride.setStatus(Status.COMPLETED);
        ride.setCompletedDateTime(LocalDateTime.now());

        vehicleRepo.save(vehicle);
        driverRepo.save(driver);
        rideRepo.save(ride);

        return "Ride completed successfully.";
    }
}
