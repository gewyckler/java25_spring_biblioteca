package pl.javagda25.spring.la_biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/")// domyslnie request mapping jest na "/"
public class IndexController {

    @GetMapping("/") // domyslnie request mapping jest na "/"
    public String index() {
        return "index";
    }
}
