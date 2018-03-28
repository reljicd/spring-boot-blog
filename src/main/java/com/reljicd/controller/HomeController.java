package com.reljicd.controller;

import com.reljicd.model.Post;
import com.reljicd.service.PostService;
import com.reljicd.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HomeController {

    private static final int INITIAL_PAGE = 0;

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam("page") Optional<Integer> page) {

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param decreased by 1.
        int pageNumber = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Post> posts = postService.findAllOrderedByDatePageable(pageNumber);
        Pager pager = new Pager(posts);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
