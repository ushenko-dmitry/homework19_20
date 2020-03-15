package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.ItemRepository;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemStatus;
import ru.mail.dimaushenko.service.ItemConvertService;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.model.ItemDTO;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;
    private final ItemConvertService itemConvertService;

    public ItemServiceImpl(ItemRepository itemRepository, ItemConvertService itemConvertService) {
        this.itemRepository = itemRepository;
        this.itemConvertService = itemConvertService;
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConvertService.getObjectFromDTO(itemDTO);
                item = itemRepository.addEntity(connection, item);
                itemDTO = itemConvertService.getDTOFromObject(item);
                connection.commit();
                return itemDTO;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.getAllEntities(connection);
                List<ItemDTO> itemDTOs = itemConvertService.getDTOFromObject(items);
                connection.commit();
                return itemDTOs;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public List<ItemDTO> getCompletedItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Item> items = itemRepository.getEntityByItemStatus(connection, ItemStatus.COMPLETED);
                List<ItemDTO> itemDTOs = itemConvertService.getDTOFromObject(items);
                connection.commit();
                return itemDTOs;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public ItemDTO getItemById(Long itemId) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemRepository.getEntityById(connection, itemId);
                if (item == null) {
                    return null;
                }
                ItemDTO itemDTO = itemConvertService.getDTOFromObject(item);
                connection.commit();
                return itemDTO;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Integer getAmountItems() {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer amountItems = itemRepository.getAmountEntities(connection);
                connection.commit();
                return amountItems;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean isItemFound(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConvertService.getObjectFromDTO(itemDTO);
                boolean isFound = itemRepository.isItemFound(connection, item);
                connection.commit();
                return isFound;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }
    
    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConvertService.getObjectFromDTO(itemDTO);
                itemRepository.updateEntity(connection, item);
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean removeItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConvertService.getObjectFromDTO(itemDTO);
                itemRepository.removeEntity(connection, item);
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

}
