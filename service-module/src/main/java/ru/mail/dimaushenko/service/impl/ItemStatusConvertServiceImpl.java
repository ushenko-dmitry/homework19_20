package ru.mail.dimaushenko.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import ru.mail.dimaushenko.repository.model.ItemStatus;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;
import ru.mail.dimaushenko.service.ItemStatusConvertService;

@Component
public class ItemStatusConvertServiceImpl implements ItemStatusConvertService {

    @Override
    public ItemStatusDTO getDTOFromObject(ItemStatus itemStatus) {
        if (itemStatus != null) {
            switch (itemStatus) {
                case COMPLETED:
                    return ItemStatusDTO.COMPLETED;
                case READY:
                    return ItemStatusDTO.READY;
            }
        }
        return null;
    }

    @Override
    public List<ItemStatusDTO> getDTOFromObject(List<ItemStatus> itemStatuses) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemStatus getObjectFromDTO(ItemStatusDTO itemStatusDTO) {
        if (itemStatusDTO != null) {
            switch (itemStatusDTO) {
                case COMPLETED:
                    return ItemStatus.COMPLETED;
                case READY:
                    return ItemStatus.READY;
            }
        }
        return null;
    }

    @Override
    public List<ItemStatus> getObjectFromDTO(List<ItemStatusDTO> itemStatusDTOs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
