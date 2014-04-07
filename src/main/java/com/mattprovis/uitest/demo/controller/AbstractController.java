package com.mattprovis.uitest.demo.controller;

import com.mattprovis.uitest.demo.service.ShoppingCartService;
import com.mattprovis.uitest.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;


public abstract class AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ModelAttribute("userName")
    public String getUserName() {
        return userService.getCurrentUser().getName();
    }

    @ModelAttribute("isLoggedIn")
    public boolean isAuthorised() {
        return userService.getCurrentUser().isAuthorised();
    }

    @ModelAttribute("lastLoggedInTime")
    public String lastLoggedInTime() {
        return userService.getCurrentUser().getLastLoggedInTime();
    }

    @ModelAttribute("shoppingCartItemsCount")
    public int getShoppingCartItemsCount() {
        return shoppingCartService.getItemsCount();
    }
}