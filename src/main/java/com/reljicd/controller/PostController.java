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

/**
 * Created by Dusan on 19-May-17.
 */
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
        User user = userService.findByUsername(principal.getName());
        Post post = new Post();
        post.setUser(user);
        modelAndView.addObject("post", post);
        modelAndView.setViewName("/postForm");
        return modelAndView;
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public ModelAndView createNewPost(@Valid Post post, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/postForm");
        } else {
            postService.savePost(post);
            modelAndView.setViewName("redirect:/blog/" + post.getUser().getUsername());
        }
        return modelAndView;
    }

    /**
     * Edit post with provided id.
     * It is not possible to edit if the user is not authenticated
     * and if he is now the owner of the post
     *
     * @param id
     * @param principal
     * @return post model and postForm view, for editing post
     */
    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
    public ModelAndView editPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.findPostForId(id);
        // Not possible to edit if user is not logged in, or if he is now the owner of the post
        if (principal == null || !principal.getName().equals(post.getUser().getUsername())) {
            modelAndView.setViewName("/403");
        }
        if (post == null) {
            modelAndView.setViewName("/404");
        } else {
            modelAndView.addObject("post", post);
            modelAndView.setViewName("/postForm");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ModelAndView getPostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.findPostForId(id);
        // Add username info to modelAndView only if the visitor of page is the owner of post
        if (principal != null && principal.getName().equals(post.getUser().getUsername())) {
            modelAndView.addObject("username", principal.getName());
        }
        if (post == null) {
            modelAndView.setViewName("/404");
        } else {
            modelAndView.addObject("post", post);
            modelAndView.setViewName("/post");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ModelAndView deletePostWithId(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Post post = postService.findPostForId(id);
        if (post == null) {
            modelAndView.setViewName("/error");
        }
        // Not possible to delete if user is not logged in, or if he is now the owner of the post
        else if (principal == null || !principal.getName().equals(post.getUser().getUsername())) {
            modelAndView.setViewName("/403");
        } else {
            postService.delete(post);
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }
}
