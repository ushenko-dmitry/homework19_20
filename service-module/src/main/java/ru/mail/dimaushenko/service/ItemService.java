package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemPaginationDTO;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;

public interface ItemService {

    ItemDTO addItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    List<ItemDTO> getAllItems(ItemPaginationDTO paginationDTO);

    List<ItemDTO> getCompletedItems();

    List<ItemDTO> getCompletedItems(ItemPaginationDTO paginationDTO);

    ItemDTO getItemByUUID(String itemUuid);

    Integer getAmountItems();

    Integer getAmountItems(ItemStatusDTO itemStatusDTO);

    boolean isItemFound(ItemDTO itemDTO);

    boolean updateItem(ItemDTO itemDTO);

    boolean removeItem(ItemDTO itemDTO);

}
