package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    @GetMapping("cart")
    public String postponedPage(Model model){
        return "cart";
    }
}
