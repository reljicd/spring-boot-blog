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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by Dusan on 19-May-17.
 */
@Controller
public class BlogController {

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 5;
    private static final int[] PAGE_SIZES = {5, 10, 20};

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
    public ModelAndView blogForUsername(@PathVariable String username,
                                        @RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page) {
        // Evaluate page size. If requested parameter is null, return initial
        // page size
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findByUsername(username);
        if (user == null) {
            modelAndView.setViewName("404");
        } else {
            Page<Post> posts = postService.findByUserOrderedByDatePageable(user, new PageRequest(evalPage, evalPageSize));
            Pager pager = new Pager(posts.getTotalPages(), posts.getNumber(), BUTTONS_TO_SHOW);

//            modelAndView.addObject("posts", postService.findNLatestPostsForUser(10, user));
            modelAndView.addObject("posts", posts);
            modelAndView.addObject("selectedPageSize", evalPageSize);
            modelAndView.addObject("pageSizes", PAGE_SIZES);
            modelAndView.addObject("pager", pager);
            modelAndView.addObject("user", user);
            modelAndView.setViewName("posts");
        }
        return modelAndView;
    }
}
