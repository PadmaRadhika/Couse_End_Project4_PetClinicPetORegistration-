package com.example.petclinicpetregistration.service;

import java.util.Optional;

import com.example.petclinicpetregistration.model.Pet;

public interface PetService {
	Pet savePet(Pet pet);
    Optional<Pet> getPetById(Integer id);
}
