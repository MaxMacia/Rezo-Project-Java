package com.maxence_macia.RezoProjectJava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.maxence_macia.RezoProjectJava.services.AuthenticationService;
import com.maxence_macia.RezoProjectJava.auth.*;
import com.maxence_macia.RezoProjectJava.entities.Role;

@SpringBootApplication
public class RezoProjectJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RezoProjectJavaApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(AuthenticationService service) {
		return args -> {
			var registerAdmin = new RegisterRequest();
			registerAdmin.setLogin("Admin");
			registerAdmin.setEmail("admin@mail.com");
			registerAdmin.setPassword("Admin");
			registerAdmin.setRole(Role.ADMIN);
			var adminInDB = service.getUserRepository().findByLogin(registerAdmin.getLogin()).orElse(null);
			
			if(adminInDB == null) {
				System.out.println("Admin token: " + service.register(registerAdmin).getAccesstoken());
			} else {
				var authAdmin = new AuthenticationRequest();
				authAdmin.setLogin(registerAdmin.getLogin());
				authAdmin.setPassword(registerAdmin.getPassword());
				System.out.println("Admin token: " + service.authenticate(authAdmin).getAccesstoken());
			}
		};
	}
}
