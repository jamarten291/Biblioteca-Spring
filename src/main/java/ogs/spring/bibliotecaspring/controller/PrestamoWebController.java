package ogs.spring.bibliotecaspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PrestamoWebController {
    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
}
