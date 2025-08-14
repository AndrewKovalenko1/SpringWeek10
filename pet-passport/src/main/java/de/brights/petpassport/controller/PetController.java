package de.brights.petpassport.controller;

import de.brights.petpassport.model.Owner;
import de.brights.petpassport.model.Pet;
import de.brights.petpassport.repository.OwnerRepository;
import de.brights.petpassport.repository.PetRepository;
import de.brights.petpassport.service.PetService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PetController {

    private final PetService petService;
    private final PetRepository petRepository;
    private  final OwnerRepository ownerRepository;

    public PetController(PetService petService, OwnerRepository ownerRepository, PetRepository petRepository) {
        this.petService = petService;
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    @PostMapping("/api/pets")
    public ResponseEntity<String> addPet(@Valid @RequestBody Pet pet) {
        try {
            petService.addPet(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pet added!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //keine ownerID
        } catch (IllegalStateException e) {
            return ResponseEntity.status(404).body(e.getMessage()); //keine Owner
        }
    }

    @GetMapping("/api/pets")
    public ResponseEntity<?> getAllPets(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        if (page == null || size == null) {
            return ResponseEntity.ok(petRepository.findAll(Sort.by(sortBy)));
        }

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Pet> petsPage = petRepository.findAll(pageable);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("content", petsPage.getContent());

        Map<String, Object> pageInfo = new LinkedHashMap<>();
        pageInfo.put("currentPage", petsPage.getNumber());
        pageInfo.put("totalItems", petsPage.getTotalElements());
        pageInfo.put("totalPages", petsPage.getTotalPages());
        pageInfo.put("pageSize", petsPage.getSize());
        pageInfo.put("sort", sortBy + " " + sortDir.toUpperCase());

        response.put("pageInfo", pageInfo);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/pets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return petRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/pets/{id}")
    @Transactional
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody Pet updatedPet) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(updatedPet.getName());
                    existingPet.setSpecies(updatedPet.getSpecies());
                    existingPet.setBirthDate(updatedPet.getBirthDate());

                    if (updatedPet.getOwner() != null) {
                        if (updatedPet.getOwner().getId() != 0) {
                            // Якщо owner існує — підвантажуємо його з БД
                            Owner managedOwner = ownerRepository.findById(updatedPet.getOwner().getId())
                                    .orElseThrow(() -> new RuntimeException("Owner not found"));
                            existingPet.setOwner(managedOwner);
                        } else {
                            // Новий owner
                            existingPet.setOwner(updatedPet.getOwner());
                        }
                    }

                    Pet savedPet = petRepository.save(existingPet);
                    return ResponseEntity.ok(savedPet);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/pets/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return ResponseEntity.ok("Pet mit ID " + id + " wurde erfolgreich gelöscht.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pet mit ID " + id + " wurde nicht gefunden.");
        }
    }

    @GetMapping("/api/pets/search")
    public ResponseEntity<List<Pet>> searchPets(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String ownerName) {



        List<Pet> pets = petRepository.findAll().stream()
                .filter(p -> name == null || p.getName().equalsIgnoreCase(name))
                .filter(p -> species == null || p.getSpecies().name().equalsIgnoreCase(species))
                .filter(p -> ownerName == null ||
                        p.getOwner() != null &&
                                p.getOwner().getFullName().toLowerCase().contains(ownerName.toLowerCase()))
                .toList();

        if (pets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(pets);
    }
}
