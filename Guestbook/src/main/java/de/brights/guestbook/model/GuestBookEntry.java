package de.brights.guestbook.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GuestBookEntry {

    @NotBlank (message = "Please write a name!")
    private String name;
    @Size(min = 5, message = "Message must be minimum 5 symbols lang")
    private String message;
    private LocalDateTime date;

}
