package ru.mail.dimaushenko.webmodule.controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.UserDTO;
import ru.mail.dimaushenko.service.model.ItemPaginationDTO;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;

@Controller
public class ItemController {

    private static final Integer DEFAULT_CURRENT_PAGE = 1;
    private static final Integer DEFAULT_ITEMS_PER_PAGE = 5;
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
            @ModelAttribute(name = "paginations") ItemPaginationDTO pagination,
            Authentication authentication,
            Model model) {
        setPagination(pagination);

        UserDTO userDTO = userService.getUserDTOByUsername(authentication.getName());

        Integer amountItems = null;
        List<ItemDTO> itemDTOs = new ArrayList<>();
        switch (userDTO.getRole()) {
            case ADMIN:
                amountItems = itemService.getAmountItems();
                itemDTOs = itemService.getAllItems(pagination);
                break;
            case USER:
                amountItems = itemService.getAmountItems(ItemStatusDTO.COMPLETED);
                itemDTOs = itemService.getCompletedItems(pagination);
                break;
        }

        Integer amountPages = amountItems / pagination.getItemsPerPage();
        if (amountItems % pagination.getItemsPerPage() > 0) {
            amountPages++;
        }
        if (amountPages == 0) {
            amountPages++;
        }
        pagination.setAmountPages(amountPages);

        model.addAttribute("items", itemDTOs);
        model.addAttribute("pagination", pagination);
        return "items";
    }

    private void setPagination(ItemPaginationDTO pagination) {
        if (pagination.getCurrentPage() == null) {
            pagination.setCurrentPage(DEFAULT_CURRENT_PAGE);
        }
        if (pagination.getItemsPerPage() == null) {
            pagination.setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
        }
    }

}
