package de.brights.petpassport.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, message = "Pet hat zu kurz Name")
    @NotNull(message = "Pet muss eine Name haben")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Pet muss eine Specie haben")
    private Species species;

    @NotNull(message = "Pet muss eine Geburtsdatum haben")
    @Past(message = "Die Geburtsdatum vom Pet muss in der Vergangenheit sein")
    private LocalDate birthDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Pet(String name, Species species, LocalDate birthDate, Owner owner) {
        this.name = name;
        this.species = species;
        this.birthDate = birthDate;
        this.owner = owner;
    }


}
