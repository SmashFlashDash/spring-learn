package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TagsPageController {
    private final BookService bookService;
    @Autowired
    private TagsPageController(BookService bookService){
        this.bookService = bookService;
    }
    @ModelAttribute("active")
    public String active(){
        return "Tags";
    }


}
