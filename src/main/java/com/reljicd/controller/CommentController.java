package com.reljicd.controller;

import com.reljicd.model.Comment;
import com.reljicd.model.Post;
import com.reljicd.model.User;
import com.reljicd.service.CommentService;
import com.reljicd.service.PostService;
import com.reljicd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    /**
     * Save comment with body from form
     */
    @RequestMapping(value = "/createComment", method = RequestMethod.POST)
    public ModelAndView createNewPost(@Valid Comment comment, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/commentForm");
        } else {
            commentService.saveComment(comment);
            modelAndView.setViewName("redirect:/post/" + comment.getPost().getId());
        }
        return modelAndView;
    }

    /**
     * Comment post with provided id.
     */
    @RequestMapping(value = "/commentPost/{id}", method = RequestMethod.GET)
    public ModelAndView commentPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Post> post = postService.findPostForId(id);
        if (post.isPresent()) {
            Optional<User> user = userService.findByUsername(principal.getName());
            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());
                modelAndView.addObject("comment", comment);
                modelAndView.setViewName("/commentForm");
            } else {
                modelAndView.setViewName("/error");
            }
        } else {
            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }

}
