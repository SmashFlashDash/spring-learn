package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TagsController {
    private final BookService bookService;
    @Autowired
    public TagsController(BookService bookService) {
        this.bookService = bookService;
    }
    @ModelAttribute("active")
    public String activePage() {
        return "Null";
    }

    @GetMapping(value = {"/tags/{tagWord}"})
    public String getTags(@PathVariable(value = "tagWord") String tagWord, Model model){
        model.addAttribute("newBooks", bookService.getPageOfPopularBooks(10, 10));
        return "/tags/index";
    }
}
