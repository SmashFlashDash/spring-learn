package com.example.MyBookShopApp.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SpringfoxConfig {
    @Bean
    public Docket docket(){   // бин от springfox в котором натсраиваются
        // параметры генерируемой документации
        return new Docket(DocumentationType.SWAGGER_2)  // тип докментации
                .select() // select чтобы выбрать API селектор билдер - обьект
                // который строит документацию
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // документировать только классы которые аннотированы @Api
                .paths(PathSelectors.ant("/api/*"))
                // документирует только методы в EndPoint которые есть приствка Api
                .build()   // завершает построение документации
                .apiInfo(apiInfo());    // добавим класс apiInfo()
    }

    public ApiInfo apiInfo(){
        return new ApiInfo(
                "Bookshop API",         // заголово API
                "API for bookstore",   // описание API
                "1.0",  // версия
                "http://www.termsofservice.org",  // url для условий пользования
                // у нас нет напишем ссылку
                new Contact("API owner", "http://www.ownersite.com", "owner@mail.com"),
                // контактные данные обьектом Contact, имя, url на сайт, адрес почты
                "api_license", // название лицензии
                "http://www.license.edu.org",  // лицензия
                new ArrayList<>()  // расширения вендора у нас нет передадим пустой лист
        );
    }
}
