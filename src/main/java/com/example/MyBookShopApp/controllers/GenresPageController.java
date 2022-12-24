package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenresPageController {
//    Дерево жанров технически представляет собой блок ссылок, а вложенность достигается путём
//    включения одних блоков и групп блоков в другие parent-блоки.
//    Каждая ссылка в блоке ведёт на страницу /genres/slug.html, в которой при помощи
//    механизма пагинации осуществляется вывод книг, обладающих выбранным из облака жанром.
//    Для реализации этой задачи вам понадобится хранить данные по жанрам (список жанров)
//    и осуществлять их мэппинг с данными из таблицы books. Далее, выполняя запрос на выборку книг
//    с определённым жанром и анализируя результаты, вы сможете определять контент — список книг из
//    books для вывода на странице сайта при помощи уже имеющегося в вашем распоряжении механизма
//    пагинации контента.
//    Рендеринг дерева жанров может быть осуществлён средствами Thymeleaf или JQuery на ваш выбор.
    @ModelAttribute("active")
    public String active(){
        return "Genres";
    }


    @GetMapping("")
    public String postponedPage(Model model){
        return "/genres/index";
    }
}
