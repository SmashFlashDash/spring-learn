package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.data.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class BooksPageController {

    private final BookService bookService;
    @Autowired
    private BooksPageController(BookService bookService){
        this.bookService = bookService;
    }
//    @InitBinder
//    public void initBinder(@RequestParam("from") String from, @RequestParam("to") String to,  WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//
//    }

    @GetMapping("/books/recommended/page")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }
    @GetMapping(value = "/books/recent/page", params = {"from","to","offset","limit"})
    @ResponseBody
    public BooksPageDto getNewBooksPage(
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern="dd.MM.yyyy") Date from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern="dd.MM.yyyy") Date to,
//            @RequestParam(name = "from", required = false) String fromString,
//            @RequestParam(name = "to", required = false) String toString,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//        dateFormat.setLenient(false);
//        Date from;
//        try {
//            from = dateFormat.parse(fromString);
//        } catch (Exception e){
//            from = null;
//        }
//        Date to;
//        try {
//            to = dateFormat.parse(fromString);
//        } catch (Exception e){
//            to = null;
//        }
        // TODO: первый запрос со страницы возвращает limit = 20
        if (from == null && to == null){
            return new BooksPageDto(bookService.getPageOfNewBooks(offset, limit).getContent());
        } else if (from != null && to != null){
            return new BooksPageDto(bookService.getPageOfNewBooksDateBetween(from, to, offset, limit).getContent());
        } else if (from != null){
            return new BooksPageDto(bookService.getPageOfNewBooksDateFrom(from, offset, limit).getContent());
        } else {
            return new BooksPageDto(bookService.getPageOfNewBooksDateTo(to, offset, limit).getContent());
        }
    }
    @GetMapping("/books/popular/page")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit){
//        Метод выводит книги по убыванию популярности — от самой большой популярности до самой маленькой.
//        См. “Алгоритм определения популярности книг”.
//        Книги, у которых популярность равна 0, можно выводить в произвольном порядке без какой-либо специальной сортировки.
//        Параметры:
//        offset — сдвиг от 0 для постраничного вывода
//        limit — количество книг, которое надо вывести
//        Формат ответа:
//        {
//	        ‘count’: 390,
//	        ‘books’: [
//            BOOK,]
//        }
//        Алгоритм определения популярности книг
//        Популярность книги представляет собой неотрицательное число, которое можно рассчитать по следующей формуле:
//        P = B + 0,7*C + 0,4*K,
//        где B — количество пользователей, купивших книгу,
//        C — количество пользователей, у которых книга находится в корзине, а
//        K — количество пользователей, у которых книга отложена.
        return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }

//    MainPageController
//    Облако тегов технически представляет собой блок ссылок, а популярность тега визуализируется при
//    помощи размера шрифта, рассчитанного по количеству книг, обладающих рассматриваемым тегом.
//    Каждая ссылка в блоке ведёт на страницу /tags/index.html, в которой при помощи механизма пагинации
//    осуществляется вывод книг, обладающих выбранным из облака тегом.
//    Для реализации этой задачи вам понадобится хранить данные по тегам (список тегов) и осуществлять их
//    мэппинг с данными из таблицы books. Далее, выполняя запрос на выборку книг с определённым тегом и
//    анализируя результаты, вы сможете определять размер тега в облаке. Рендеринг облака тегов может
//    быть осуществлён средствами Thymeleaf или JQuery на ваш выбор.

    @GetMapping("/books/popular")
    public String getPopularBooks(Model model){
        model.addAttribute("active", "Popular");
        model.addAttribute("popularBooks", bookService.getPageOfPopularBooks(0, 10).getContent());
        return "/books/popular";
    }
    @RequestMapping("/books/recent")
    public String getNewBooks(Model model){
        model.addAttribute("active", "Recent");
        model.addAttribute("newBooks", bookService.getPageOfNewBooks(0, 10).getContent());
        return "/books/recent";
    }


}
