package de.brights.petpassport;

import de.brights.petpassport.model.Owner;
import de.brights.petpassport.model.Pet;
import de.brights.petpassport.model.Species;
import de.brights.petpassport.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;

    @Autowired
    public DataLoader(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void run(String... args) {
        Owner owner1 = new Owner("Owner1", "email1@mail1.com", "+416000000000000");
        Owner owner2 = new Owner("Owner2", "email1@mail2.com", "+416000000000001");
        Pet pet1 = new Pet("Pet1", Species.CAT, LocalDate.of(2020, 5, 15), owner1);
        Pet pet2 = new Pet("Pet2", Species.DOG, LocalDate.of(2021, 5, 15), owner1);
        Pet pet3 = new Pet("Pet3", Species.PARROT, LocalDate.of(2022, 5, 15), owner1);
        Pet pet4 = new Pet("Pet4", Species.CAT, LocalDate.of(2023, 5, 15), owner2);
        Pet pet5 = new Pet("Pet5", Species.DOG, LocalDate.of(2024, 5, 15), owner2);
        owner1.getPets().add(pet1);
        owner1.getPets().add(pet2);
        owner1.getPets().add(pet3);
        owner2.getPets().add(pet4);
        owner2.getPets().add(pet5);
        this.ownerRepository.save(owner1);
        this.ownerRepository.save(owner2);

    }
}
