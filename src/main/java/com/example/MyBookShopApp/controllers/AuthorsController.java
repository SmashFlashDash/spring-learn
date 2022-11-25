package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.author.Author;
import com.example.MyBookShopApp.data.services.AuthorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/authors")
@Api(description = "authors data")  // @Api чтобы класс попал в докуметаци
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }

    @GetMapping("")
    public String authorsPage(){
        return "/authors/index";
    }

    // добавим этот метод чтобы он попал в документацию API
    @GetMapping("/api/authors")
    public Map<String,List<Author>> authors(){
        return authorService.getAuthorsMap();
    }
}
