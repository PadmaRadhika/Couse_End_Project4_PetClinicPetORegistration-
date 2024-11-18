package com.example.petclinicpetregistration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.petclinicpetregistration.dao.PetRepository;

import com.example.petclinicpetregistration.model.Pet;


@Service
public class PetServiceImpl implements PetService{
	
	@Autowired
	private PetRepository petRepository;

	@Override
	public Pet savePet(Pet pet) {
		// TODO Auto-generated method stub
		return petRepository.save(pet);
	}

	@Override
	public Optional<Pet> getPetById(Integer id) {
		// TODO Auto-generated method stub
		return petRepository.findById(id);
	}
	

}
