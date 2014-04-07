package com.mattprovis.uitest.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ShoppingCartController extends AbstractController {

    @RequestMapping(value="/shoppingCart", method= RequestMethod.GET)
    public String showShoppingCart() {
        return "shoppingCart";
    }
}
