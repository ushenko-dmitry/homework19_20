package ru.mail.dimaushenko.webmodule.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.UserDTO;

@Controller
public class ItemController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final ItemService itemService;

    public ItemController(
            UserService userService,
            ItemService itemService
    ) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String getItems(
            Authentication authentication,
            Model model) {
        UserDTO userDTO = userService.getUserByUsername(authentication.getName());
        if (userDTO == null) {
            logger.error("User is not found");
            return "redirect:/logout";
        }
        List<ItemDTO> items = getItems(userDTO);
        model.addAttribute("items", items);
        return "items";
    }

    private List<ItemDTO> getItems(UserDTO userDTO) {
        List<ItemDTO> items = null;
        switch (userDTO.getRole()) {
            case ADMIN:
                items = itemService.getAllItems();
                break;
            case USER:
                items = itemService.getCompletedItems();
                break;
        }
        return items;
    }

}
