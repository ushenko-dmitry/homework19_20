package ru.mail.dimaushenko.webmodule.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;

@RestController
@RequestMapping("/api/items")
public class ItemApiController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemService itemService;

    public ItemApiController(
            ItemService itemService
    ) {
        this.itemService = itemService;
    }

    @GetMapping()
    public ResponseEntity showItems() {
        List<ItemDTO> items = itemService.getAllItems();
        return new ResponseEntity(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity showItem(@PathVariable(name = "id") String id) {
        ItemDTO item = itemService.getItemById(Long.parseLong(id));
        if (item == null) {
            return new ResponseEntity("Item is not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(item, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    public ResponseEntity addItem(@RequestBody ItemDTO item) {
        ItemDTO addItem = itemService.addItem(item);
        return new ResponseEntity(addItem, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public Object updateItem(@RequestBody ItemDTO item) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompletedItem(@PathVariable(name = "id") String id) {
        ItemDTO item = itemService.getItemById(Long.parseLong(id));
        if (item.getStatus() != ItemStatusDTO.COMPLETED) {
            return new ResponseEntity("Item is not completed", HttpStatus.BAD_REQUEST);
        }
        boolean isRemoveItem = itemService.removeItem(item);
        if (isRemoveItem) {
            return new ResponseEntity("Item is not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(item, HttpStatus.OK);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity deleteCompletedItems() {
        List<ItemDTO> completedItems = itemService.getCompletedItems();
        for (ItemDTO item : completedItems) {
            itemService.removeItem(item);
        }
        return new ResponseEntity("Items has deleted", HttpStatus.OK);
    }

}
