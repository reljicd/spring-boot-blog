package com.reljicd.repository;

import com.reljicd.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for {@link Comment} domain objects
 *
 * @author Dusan
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
