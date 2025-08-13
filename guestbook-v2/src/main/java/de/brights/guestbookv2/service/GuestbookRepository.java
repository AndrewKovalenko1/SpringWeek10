package de.brights.guestbookv2.service;

import de.brights.guestbookv2.model.GuestBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<GuestBookEntry, Long> {
}
