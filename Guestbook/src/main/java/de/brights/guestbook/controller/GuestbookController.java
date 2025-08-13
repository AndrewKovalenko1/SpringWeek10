package de.brights.guestbook.controller;

import de.brights.guestbook.model.GuestBookEntry;
import de.brights.guestbook.service.GuestBookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GuestbookController {

    private final GuestBookService guestBookService;

    public GuestbookController(GuestBookService guestBookService) {
        this.guestBookService = guestBookService;
    }

    @GetMapping("/guestbook")
    @ResponseBody
    public ResponseEntity<List<GuestBookEntry>> getAllEntries() {
        return ResponseEntity.ok(guestBookService.getAllEntries());
    }

    @DeleteMapping("/guestbook")
    public ResponseEntity<String> deleteAllEntries() {
        this.guestBookService.clearEntries();
        return ResponseEntity.ok("Alle Einträge gelöscht!");
    }

    @PostMapping("/guestbook")
    public ResponseEntity<String> addEntry(@Valid @RequestBody GuestBookEntry entry, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder("Validation failed");
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errMsg.append("\n").append(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errMsg.toString());
        }
        this.guestBookService.addEntry(entry.getName(), entry.getMessage());

        LocalTime jetzt = LocalTime.now();
        String greeting;
        if (jetzt.isBefore(LocalTime.NOON)) {
            greeting = "Guten Morgen";
        } else {
            greeting = "Guten Abend";
        }

        return  ResponseEntity.ok(greeting + ", " + entry.getName() + "! Danke für deinen Eintrag.");

    }
}
