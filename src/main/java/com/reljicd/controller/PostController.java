package com.reljicd.controller;

import com.reljicd.model.Post;
import com.reljicd.model.User;
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
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.GET)
    public ModelAndView newPost(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<User> user = userService.findByUsername(principal.getName());
        if (user.isPresent()) {
            Post post = new Post();
            post.setUser(user.get());
            modelAndView.addObject("post", post);
            modelAndView.setViewName("/postForm");
        } else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public ModelAndView createNewPost(@Valid Post post, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/postForm");
        } else {
            postService.save(post);
            modelAndView.setViewName("redirect:/blog/" + post.getUser().getUsername());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
    public ModelAndView editPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Post> optionalPost = postService.findForId(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPost(principal, post)) {
                modelAndView.addObject("post", post);
                modelAndView.setViewName("/postForm");
            } else {
                modelAndView.setViewName("/403");
            }
        } else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ModelAndView getPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Post> optionalPost = postService.findForId(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPost(principal, post)) {
                modelAndView.addObject("username", principal.getName());
            }
            modelAndView.addObject("post", post);
            modelAndView.setViewName("/post");

        } else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ModelAndView deletePostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Post> optionalPost = postService.findForId(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (isPrincipalOwnerOfPost(principal, post)) {
                postService.delete(post);
                modelAndView.setViewName("redirect:/home");
            } else {
                modelAndView.setViewName("/403");
            }
        } else {
            modelAndView.setViewName("/error");
        }

        return modelAndView;
    }

    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getUsername());
    }
}
