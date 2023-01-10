package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookService;
import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.data.tag.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
public class TagsPageController {
    private final BookService bookService;
    @Autowired
    public TagsPageController(BookService bookService) {
        this.bookService = bookService;
    }
    @ModelAttribute("active")
    public String activePage() {
        return "Null";
    }

    @GetMapping("/books/tags/page/{tagWord}")
    @ResponseBody
    public BooksPageDto getPageBooksTag(@PathVariable(value = "tagWord", required = false) TagDto tagWord,
                                      @RequestParam("offset") Integer offset,
                                      @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfBooksByTag(tagWord.getTag(), offset, limit).getContent());
    }

    @GetMapping("/tags/{tagWord}")
    public String getBooksTag(@PathVariable(value = "tagWord") TagDto tagWord, Model model){
        model.addAttribute("TagDto", tagWord);
        model.addAttribute("tagBooks", bookService.getPageOfBooksByTag(tagWord.getTag(), 0, 10).getContent());
        return "/tags/index";
    }
}
