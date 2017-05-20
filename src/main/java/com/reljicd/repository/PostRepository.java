package com.reljicd.repository;

import com.reljicd.model.Post;
import com.reljicd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository class for {@link Post} domain objects
 *
 * @author Dusan
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Retrive {@link Page) of {@link Post}s from data store that are owned by user and ordered by date
     *
     * @param user     that ownes {@link Post}s
     * @param pageable
     * @return {@link Page) of {@link Post}s
     */
    Page<Post> findByUserOrderByCreateDateDesc(User user, Pageable pageable);

    /**
     * Retrive {@link Page) of all {@link Post}s from data store ordered by date
     *
     * @param user     post that user ownes
     * @param pageable
     * @return {@link Page) of {@link Post}s
     */
    Page<Post> findAllByOrderByCreateDateDesc(Pageable pageable);
}
