package com.mattprovis.uitest.demo.controller;

import com.mattprovis.uitest.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends AbstractController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String showHome() {
        return "home";
    }

    @ModelAttribute("latestNews")
    public String getLatestNews() {
        return newsService.getLatestNews();
    }

    @RequestMapping(value="/exception", method= RequestMethod.GET)
    public String throwException() {
        throw new ExampleException();
    }

    @RequestMapping(value="/error", method= RequestMethod.GET)
    public String throwError() {
        throw new ExampleError();
    }

    public class ExampleException extends RuntimeException {
    }

    public class ExampleError extends Error {
    }
}
