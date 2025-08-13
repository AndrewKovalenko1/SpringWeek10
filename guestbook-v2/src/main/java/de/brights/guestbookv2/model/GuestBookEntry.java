package de.brights.guestbookv2.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class GuestBookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please write a name!")
    private String name;
    @Size(min = 5, message = "Message must be minimum 5 symbols lang")
    private String message;
    private LocalDateTime date;


    public GuestBookEntry(String name, String message, LocalDateTime date) {
        this.name = name;
        this.message = message;
        this.date = date;
    }
}
