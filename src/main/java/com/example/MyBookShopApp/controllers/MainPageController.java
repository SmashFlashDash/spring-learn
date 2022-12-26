package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.search.SearchWordDto;
import com.example.MyBookShopApp.data.book.BookService;
import com.example.MyBookShopApp.data.tag.TagEntity;
import com.example.MyBookShopApp.data.tag.TagRepository;
import com.example.MyBookShopApp.data.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {
    private final BookService bookService;
    private final TagService tagService;
    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }
    @ModelAttribute("active")
    public String activePage() {
        return "Main";
    }
    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }
    @ModelAttribute("newBooks")
    public List<Book> newBooks() {
        return bookService.getPageOfNewBooks(0, 6).getContent();
    }
    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.getPageOfPopularBooks(0, 6).getContent();
    }
    @ModelAttribute("tagsBooks")
    public List<TagEntity> tagsBooks(){
        return tagService.getTagsSortedByBooksCount();
    }

    // TODO: эксепшен при двигании слайдера
    //  на страницах recent не написан href для кнопки Показать еще

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

//    // Страница genres
//    @GetMapping("/genres")
//    public String getGenres(Model model){
//        return "/genres/index";
//    }
//    // Страница authors
//    @GetMapping("/authors")
//    public String getAuthors(Model model){
//        return "/authors/index";
//    }

}
