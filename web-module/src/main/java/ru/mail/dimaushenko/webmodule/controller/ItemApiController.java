package ru.mail.dimaushenko.webmodule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.dimaushenko.service.model.UserDTO;

@RestController
@RequestMapping("/api")
public class ItemApiController {

    @GetMapping("/items")
    public String showItems() {
        return "items_1";
    }

    @GetMapping("/items/item")
    public UserDTO showItem() {
        return new UserDTO();
    }

}
