package de.brights.guestbookv2.service;


import de.brights.guestbookv2.model.GuestBookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GuestBookService {

    private final GuestbookRepository guestbookRepository;

    //dependency injection
    @Autowired
    public GuestBookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    public void addEntry(String name, String message) {
        GuestBookEntry guestBookEntry = new GuestBookEntry(name, message, LocalDateTime.now());
        guestbookRepository.save(guestBookEntry);
    }

    public List<GuestBookEntry> getAllEntries() {
        return guestbookRepository.findAll().stream().sorted(Comparator.comparing(GuestBookEntry::getDate).reversed()).toList();
    }

    public void  clearEntries() {
        guestbookRepository.deleteAll();
    }

}
