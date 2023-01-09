package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.book.BooksPageDto;
import com.example.MyBookShopApp.data.book.BookService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Pattern;
import java.time.Instant;
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

    // TODO: при ресайзе страницы запрашивает этот метод а параметры ставит одни и те же из cript.min.js
    // reloadDate();
    // TODO: первый запрос со страницы возвращает limit = 20, должен 10
    // прописан в thymeleaf на datePicker
    //    if (!$this.hasClass('form-input_date_uninit')) {
    //        $refreshoffset.data('refreshoffset', 0)
    //        getData('/books/recent/page', {
    //                from: $('[data-refreshfrom]').data('refreshfrom'),
    //                to: $('[data-refreshto]').data('refreshto'),
    //                offset: $this.data('refreshoffset'),
    //                limit: $this.data('refreshlimit')
    //                                        }, 'changedate');
    // TODO: hide кнопки когда книги кончились
    // посмотреть в thymelaf название кнопки и в js поискать
    // или в thymeleaf
    // TODO: косяк в запросе может вернуть книгу которая был показана несколько раз
    @GetMapping(value = "/books/recent/page")
    @ResponseBody
    public BooksPageDto getNewBooksPage(
            @RequestParam(name = "from", required = false)
            @Pattern(regexp="\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d$", message="wrong format") @DateTimeFormat(pattern="dd.MM.yyyy")  Date from,
            @RequestParam(name = "to", required = false)
            @Pattern(regexp="\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d$", message="wrong format") @DateTimeFormat(pattern="dd.MM.yyyy")  Date to,
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit) {
        // надо задавать дату которая будет ставится в фильтр через thymelaf
        // а в booksPageDto слать параметры
        // или на старнице убрать изначальынй текст в датах
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
        // model.addAttribute("newBooks", bookService.getPageOfNewBooks(0, 10).getContent());
        // в script.min.js  $this.datepicker().data('datepicker') запрашивает книги за последний месяц
        // можно вычислть здесь Date и кидать в model и в script.min.js брать дату а не вычислять
        model.addAttribute("newBooks", bookService.getPageOfNewBooksDateBetween(
                new DateTime().minusMonths(1).toDate(), new Date(), 0, 10).getContent());
        return "/books/recent";
    }


}
