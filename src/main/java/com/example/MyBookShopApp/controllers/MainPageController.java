package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.book.BookService;
import com.example.MyBookShopApp.data.tag.TagEntity;
import com.example.MyBookShopApp.data.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
        // @JsonIgnore в Entity или Jackson StackOverFlow Error из-за цикличного запроса
        // или возвращать DTO
        return tagService.findAllSortedByBooksCount();
    }
    // TODO: заменить href в 
//    Задание 2.3. Реализация списка книг по тегам
//    Что нужно сделать
//    На главной странице в нижней части располагается облако тегов, размер шрифта которых зависит от количества
//    книг: чем больше книг отмечено тегом, тем крупнее размер шрифта. Вы можете кликнуть на интересующий вас
//    тег и посмотреть список всех книг, им отмеченных.
//    Реализуйте необходимую логику и структуры для работы этого раздела, включая вывод списка книг по выбранному
//    тегу и само облако тегов. Кроме того, не забудьте о постраничном выводе контента в этом разделе.

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
