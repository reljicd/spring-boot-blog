package com.reljicd.service;

import com.reljicd.model.Post;
import com.reljicd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface PostService {

    Optional<Post> findPostForId(Long id);

    Post savePost(Post post);

    /**
     * Finds a {@link Page) of {@link Post} of provided user ordered by date
     */
    Page<Post> findByUserOrderedByDatePageable(User user, Pageable pageable);

    /**
     * Finds a {@link Page) of all {@link Post} ordered by date
     */
    Page<Post> findAllOrderedByDatePageable(Pageable pageable);

    void delete(Post post);
}
