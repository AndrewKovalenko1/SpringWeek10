package de.brights.guestbookv2.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GuestBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please write a name!")
    private String name;
    @NotBlank(message = "Please write a message")
    private String message;
    private LocalDateTime date;

    @JsonManagedReference
    @OneToMany(mappedBy = "guestBookEntry", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();


    public GuestBookEntry(String name, String message, LocalDateTime date) {
        this.name = name;
        this.message = message;
        this.date = date;
    }
}
