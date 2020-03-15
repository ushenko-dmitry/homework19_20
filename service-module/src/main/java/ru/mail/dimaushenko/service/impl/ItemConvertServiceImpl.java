package ru.mail.dimaushenko.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemStatus;
import ru.mail.dimaushenko.service.ItemConvertService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;

@Component
public class ItemConvertServiceImpl implements ItemConvertService {

    @Override
    public ItemDTO getDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setUuid(item.getUuid());
        itemDTO.setName(item.getName());
        itemDTO.setStatus(getItemStatusDTOFromItemStatus(item.getStatus()));
        return itemDTO;
    }

    @Override
    public List<ItemDTO> getDTOFromObject(List<Item> items) {
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            ItemDTO itemDTO = getDTOFromObject(item);
            itemDTOs.add(itemDTO);
        }
        return itemDTOs;
    }

    @Override
    public Item getObjectFromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setUuid(itemDTO.getUuid());
        item.setName(itemDTO.getName());
        item.setStatus(getItemStatusFromItemStatusDTO(itemDTO.getStatus()));
        return item;
    }

    @Override
    public List<Item> getObjectFromDTO(List<ItemDTO> itemDTOs) {
        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDTO : itemDTOs) {
            Item item = getObjectFromDTO(itemDTO);
            items.add(item);
        }
        return items;
    }

    private ItemStatusDTO getItemStatusDTOFromItemStatus(ItemStatus itemStatus) {
        if (itemStatus != null) {
            switch (itemStatus) {
                case COMPLETED:
                    return ItemStatusDTO.COMPLETED;
                case READY:
                    return ItemStatusDTO.READY;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    private ItemStatus getItemStatusFromItemStatusDTO(ItemStatusDTO itemStatusDTO) {
        if (itemStatusDTO != null) {
            switch (itemStatusDTO) {
                case COMPLETED:
                    return ItemStatus.COMPLETED;
                case READY:
                    return ItemStatus.READY;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}
