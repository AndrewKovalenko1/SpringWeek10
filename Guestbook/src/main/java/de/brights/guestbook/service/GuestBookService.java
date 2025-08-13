package de.brights.guestbook.service;

import de.brights.guestbook.model.GuestBookEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GuestBookService {

    private final List<GuestBookEntry> eintraege = new ArrayList<>();

    public void addEntry(String name, String message) {
        eintraege.add(new GuestBookEntry(name, message, LocalDateTime.now()));
    }

    public List<GuestBookEntry> getAllEntries() {
        return eintraege.stream().sorted(Comparator.comparing(GuestBookEntry::getDate).reversed()).toList();
    }

    public void  clearEntries() {
        eintraege.clear();
    }

}
