package com.reljicd.controller;

import com.reljicd.model.Post;
import com.reljicd.model.User;
import com.reljicd.service.PostService;
import com.reljicd.service.UserService;
import com.reljicd.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BlogController {

    private static final int INITIAL_PAGE = 0;

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public BlogController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
    public ModelAndView blogForUsername(@PathVariable String username,
                                        @RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int pageNumber = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user.get(), pageNumber);
            Pager pager = new Pager(posts);

            modelAndView.addObject("posts", posts);
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("user", user.get());
            modelAndView.setViewName("/posts");
        } else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }
}
