package com.reljicd.service.impl;

import com.reljicd.model.Post;
import com.reljicd.model.User;
import com.reljicd.repository.PostRepository;
import com.reljicd.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> findPostForId(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.saveAndFlush(post);
    }

    @Override
    public Page<Post> findByUserOrderedByDatePageable(User user, Pageable pageable) {
        return postRepository.findByUserOrderByCreateDateDesc(user, pageable);
    }

    @Override
    public Page<Post> findAllOrderedByDatePageable(Pageable pageable) {
        return postRepository.findAllByOrderByCreateDateDesc(pageable);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
