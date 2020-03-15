package ru.mail.dimaushenko.webmodule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {
    
    @GetMapping("/api")
    public String showAPI (){
        return "/api/items";
    }
    
}
