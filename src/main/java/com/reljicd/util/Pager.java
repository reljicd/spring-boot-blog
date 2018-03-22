package com.reljicd.util;

import com.reljicd.model.Post;
import org.springframework.data.domain.Page;

/**
 * @author Dusan Raljic
 */
public class Pager {

    private final Page<Post> posts;

    public Pager(Page<Post> posts) {
        this.posts = posts;
    }

    public int getPageIndex() {
        return posts.getNumber() + 1;
    }

    public int getPageSize() {
        return posts.getSize();
    }

    public boolean hasNext() {
        return posts.hasNext();
    }

    public boolean hasPrevious() {
        return posts.hasPrevious();
    }

    public int getTotalPages() {
        return posts.getTotalPages();
    }

    public long getTotalElements() {
        return posts.getTotalElements();
    }

    public boolean indexOutOfBounds() {
        return this.getPageIndex() < 0 || this.getPageIndex() > this.getTotalElements();
    }

}
