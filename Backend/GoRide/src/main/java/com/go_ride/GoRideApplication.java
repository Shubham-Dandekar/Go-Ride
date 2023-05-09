package com.go_ride;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Go Ride | Book your ride here", description = "Online Ride Booking System", version = "1.0"))
public class GoRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoRideApplication.class, args);
	}

}
