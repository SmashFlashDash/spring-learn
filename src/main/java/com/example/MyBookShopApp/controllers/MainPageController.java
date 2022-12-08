package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.RecommendedBooksPageDto;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks(){
        return bookService.getPageOfRecommendedBooks(0, 6)
                .getContent(); // чтобы вернуть обьекты из Page метод getContent()
    }

    @GetMapping("/")
    public String mainPage(Model model){
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public RecommendedBooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                   @RequestParam("limit") Integer limit){
        return new RecommendedBooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }
}
