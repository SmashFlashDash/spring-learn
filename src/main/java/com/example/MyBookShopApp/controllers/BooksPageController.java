package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.data.book.BooksPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BooksPageController {

    private final BookService bookService;

    @Autowired
    private BooksPageController(BookService bookService){
        this.bookService = bookService;
    }


//    @GetMapping("/recent")
//    public String recentPage(Model model){
//        model.addAttribute("recentBooks", bookService.getBooksData());
//        return "/books/recent";
//    }
//
//    @GetMapping("/popular")
//    public String popularPage(Model model){
//        model.addAttribute("popularBooks", bookService.getBooksData());
//        return "/books/popular";
//    }

    @GetMapping("/books/recommended/page")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }
    @GetMapping("/books/recent/page")
    @ResponseBody
    public BooksPageDto getNewBooksPage(@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfNewBooks(offset, limit).getContent());
    }
    @GetMapping("/books/popular/page")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }


    @GetMapping("/books/popular")
    public String getPopularBooks(Model model){
        model.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 10).getContent());
        return "/books/popular";
    }
    @RequestMapping("/books/recent")
    public String getNewBooks(Model model){
        model.addAttribute("newBooks", bookService.getPageOfNewBooks(0, 10).getContent());
        return "/books/recent";
    }


}
