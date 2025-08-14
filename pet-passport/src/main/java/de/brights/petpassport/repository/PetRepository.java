package de.brights.petpassport.repository;

import de.brights.petpassport.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
