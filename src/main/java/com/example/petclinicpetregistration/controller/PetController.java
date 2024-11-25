package com.example.petclinicpetregistration.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.petclinicpetregistration.model.Owner;
import com.example.petclinicpetregistration.model.Pet;
import com.example.petclinicpetregistration.service.PetService;

import reactor.core.publisher.Mono;

@RestController
@Configuration
@CrossOrigin(origins = "*") // Allow CORS from Angular app
@RequestMapping(value = "/pet")
public class PetController {
	
	private WebClient webClient;
	@Autowired
	private PetService petService;
	@Autowired
	private WebClient.Builder webClientBuilder;
	@PostMapping
    public ResponseEntity<?> createPet(@RequestBody Pet pet) {
		System.out.println("***pet object::"+pet.toString());
		webClient = webClientBuilder.baseUrl("http://ec2-54-86-178-106.compute-1.amazonaws.com:8085").build();		
        Mono<Owner> ownerMono =  webClient.get()
                .uri("/owner/{id}", pet.getOwnerId())
                .retrieve()                
              .bodyToMono(Owner.class);
       // Pet createdPet = null;
        try {
            // Block and get the Owner, if found
            Owner owner = ownerMono.block();

            // If the owner is found, save the pet
            Pet createdPet = petService.savePet(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);

        } catch (WebClientResponseException.NotFound e) {
            // Handle "Owner not found" scenario - 404 error if the owner does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Owner with ID " + pet.getOwnerId() + " not found");
        } catch (Exception e) {
            // Handle other errors, e.g., network issues, unexpected errors, etc.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
        
    }
	@GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
		System.out.println("**Inside PEt Controller getPetByID api call**");
        Optional<Pet> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
