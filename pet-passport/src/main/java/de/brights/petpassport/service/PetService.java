package de.brights.petpassport.service;

import de.brights.petpassport.model.Owner;
import de.brights.petpassport.model.Pet;
import de.brights.petpassport.repository.OwnerRepository;
import de.brights.petpassport.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


@Service
public class PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public PetService(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public void addPet(@Valid Pet pet) {
        if (pet.getOwner() == null || pet.getOwner().getId() == null) {
            throw new IllegalArgumentException("Pet owner is required");
        }

        Owner owner = ownerRepository.findById(pet.getOwner().getId()).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        pet.setOwner(owner);
        petRepository.save(pet);
    }
}
