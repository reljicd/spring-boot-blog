package com.reljicd.service;

import com.reljicd.model.Post;
import com.reljicd.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Service class for {@link Post} domain objects
 * Delegates calls to {@link com.reljicd.repository.PostRepository}
 *
 * @author Dusan
 */
public interface PostService {

    /**
     * Finds n latest {@link Post}s
     *
     * @param n
     * @return a Collection of {@link Post}s
     */
    Collection<Post> findNLatestPosts(int n);

    Collection<Post> findNLatestPostsForUser(int n, User user);

    Post findPostForId(Long id);

    Post savePost(Post post);

    Page<Post> findAllPageable(Pageable pageable);

    /**
     * Finds a {@link Page) of {@link Post} of provided user ordered by date
     *
     * @param user
     * @param pageable
     * @return {@link Page} instance
     */
    Page<Post> findByUserOrderedByDatePageable(User user, Pageable pageable);

    /**
     * Finds a {@link Page) of all {@link Post} ordered by date
     *
     * @param pageable
     * @return {@link Page} instance
     */
    Page<Post> findAllOrderedByDatePageable(Pageable pageable);

    /**
     * Deletes {@link Post} from data store
     *
     * @param post the {@link Post} to delete
     */
    void delete(Post post);
}
