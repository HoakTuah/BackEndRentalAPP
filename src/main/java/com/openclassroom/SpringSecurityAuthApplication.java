package com.openclassroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "RentalAppReservation", version = "1.0", description = "RentalApp Description"))
public class SpringSecurityAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthApplication.class, args);
	}

}