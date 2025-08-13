package de.brights.guestbookv2.service;

import de.brights.guestbookv2.model.Comment;
import de.brights.guestbookv2.model.GuestBookEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final GuestbookRepository guestbookRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, GuestbookRepository guestbookRepository) {
        this.commentRepository = commentRepository;
        this.guestbookRepository = guestbookRepository;
    }

    public void addComment(Long entryId, String author, String text) {
        GuestBookEntry entry = guestbookRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "GuestBookEntry mit ID " + entryId + " nicht gefunden"));
        Comment comment = new Comment(author, text, LocalDateTime.now(), entry);
        entry.getComments().add(comment);
        commentRepository.save(comment);

    }
}
