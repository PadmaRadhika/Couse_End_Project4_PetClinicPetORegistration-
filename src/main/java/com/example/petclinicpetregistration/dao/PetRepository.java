package com.example.petclinicpetregistration.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.petclinicpetregistration.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

}
