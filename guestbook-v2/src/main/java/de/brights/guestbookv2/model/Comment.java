package de.brights.guestbookv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Author of comment cant be blank")
    private String author;
    private String text;
    private LocalDateTime date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    private GuestBookEntry guestBookEntry;


    public Comment(String author, String text, LocalDateTime date, GuestBookEntry guestBookEntry) {
        this.author = author;
        this.text = text;
        this.date = date;
        this.guestBookEntry = guestBookEntry;
    }


}
