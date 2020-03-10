package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.ItemDTO;

public interface ItemService {

    ItemDTO addItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    ItemDTO getItemById(Long itemId);

    Integer getAmountItems();

    boolean updateItem(ItemDTO itemDTO);

    boolean removeItem(ItemDTO itemDTO);

}
