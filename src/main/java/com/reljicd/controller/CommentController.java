package com.reljicd.controller;

import com.reljicd.model.Comment;
import com.reljicd.model.Post;
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

/**
 * Created by Dusan on 19-May-17.
 */
@Controller
public class CommentController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    /**
     * Save comment with body from form
     *
     * @param comment
     * @param bindingResult
     * @return
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
     *
     * @param id
     * @param principal
     * @return comment model and commentForm view, for editing comment
     */
    @RequestMapping(value = "/commentPost/{id}", method = RequestMethod.GET)
    public ModelAndView commentPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.findPostForId(id);
        Comment comment = new Comment();
        if (post == null) {
            modelAndView.setViewName("/404");
        } else {
            comment.setUser(userService.findByUsername(principal.getName()));
            comment.setPost(post);
            modelAndView.addObject("comment", comment);
            modelAndView.setViewName("/commentForm");
        }
        return modelAndView;
    }

}
