package de.brights.petpassport.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Owner soll die Name haben")
    @Size(min = 3, message = "Die Name soll mind. 3 Bücher haben")
    private String fullName;

    @NotNull(message = "Owner muss ein Email haben")
    @Email(message = "Email hat schlecht Construction")
    private String email;

    @Pattern(regexp = "^\\+?\\d(?:[\\s-]?\\d){6,14}$",
    message = "Phone number nicht richtig format hat")
    private String phoneNumber;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Pet> pets = new HashSet<>();

    public Owner(String fullName, String email, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
