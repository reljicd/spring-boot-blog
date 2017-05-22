package com.reljicd.service;

import com.reljicd.model.Comment;
import com.reljicd.model.Post;
import com.reljicd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Service class for {@link com.reljicd.model.Comment} domain objects
 * Delegates calls to {@link com.reljicd.repository.CommentRepository}
 *
 * @author Dusan
 */
public interface CommentService {

    Comment saveComment(Comment comment);

}
