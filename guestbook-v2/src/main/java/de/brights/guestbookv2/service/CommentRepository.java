package de.brights.guestbookv2.service;

import de.brights.guestbookv2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
