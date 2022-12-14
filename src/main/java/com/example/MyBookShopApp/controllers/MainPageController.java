package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto(){
        // метод возвращает пустой обьект
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults(){
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(Model model){
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping(value = {"/search", "/search/{searchMod}"})
    // в качесте EndPoint обрабатывает два адреса первый /search, второй /search/параметрПоиска
    // возвращает не исписок книг а имя шаблона, и переводит нас на страницу
    // т.к. найденные книги складываются в атрибут модели и thymeleaf обрабатывает список
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto,
                                   Model model){
        model.addAttribute("searchWord", searchWordDto);    // закидываем в модель обьект
        model.addAttribute("searchResults", bookService.getPageOfSeachResultBooks(searchWordDto.getExample(), 0, 20).getContent());
        return "/search/index";
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody   // т.к. возвращаем не имя шаблона а список книг
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,   // параметры такиеже как в
                                          @RequestParam("limit") Integer limit,     // @GetMapping("/books/recommended")
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto){
                                        // обьект searchWordDto с поисковым словом
        return new BooksPageDto(bookService.getPageOfSeachResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }
}
