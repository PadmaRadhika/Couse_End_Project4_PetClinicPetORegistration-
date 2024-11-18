package com.example.petclinicpetregistration;

import org.springframework.boot.SpringApplication;

public class TestPetclinicpetregistrationApplication {

	public static void main(String[] args) {
		SpringApplication.from(PetclinicpetregistrationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
