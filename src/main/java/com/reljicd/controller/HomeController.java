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

@Controller
public class HomeController {

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(defaultValue = "0") int page) {

        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("/home");
        return modelAndView;
    }
}
