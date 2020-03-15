package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.ItemRepository;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemPagination;
import ru.mail.dimaushenko.repository.model.ItemStatus;
import ru.mail.dimaushenko.service.ItemConvertService;
import ru.mail.dimaushenko.service.ItemService;
import ru.mail.dimaushenko.service.model.ItemDTO;
import ru.mail.dimaushenko.service.model.ItemStatusDTO;
import ru.mail.dimaushenko.service.ItemStatusConvertService;
import ru.mail.dimaushenko.service.model.ItemPaginationDTO;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ItemRepository itemRepository;
    private final ItemConvertService itemConvertService;
    private final ItemStatusConvertService itemStatusConverterService;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            ItemConvertService itemConvertService,
            ItemStatusConvertService itemConverterService
    ) {
        this.itemRepository = itemRepository;
        this.itemConvertService = itemConvertService;
        this.itemStatusConverterService = itemConverterService;
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemConvertService.getObjectFromDTO(itemDTO);
                item.setUuid(UUID.randomUUID().toString());
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
    public List<ItemDTO> getAllItems(ItemPaginationDTO paginationDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemPagination pagination = getPaginationFromDTO(paginationDTO);
                List<Item> items = itemRepository.getLimitItems(connection, pagination);
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
    public List<ItemDTO> getCompletedItems(ItemPaginationDTO paginationDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemPagination pagination = getPaginationFromDTO(paginationDTO);
                List<Item> items = itemRepository.getLimitItemsByItemStatus(connection, ItemStatus.COMPLETED, pagination);
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
    public ItemDTO getItemByUUID(String itemUuid) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item item = itemRepository.getEntityByUUID(connection, itemUuid);
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
    public Integer getAmountItems(ItemStatusDTO itemStatusDTO) {
        try (Connection connection = itemRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemStatus itemStatus = itemStatusConverterService.getObjectFromDTO(itemStatusDTO);
                Integer amountItems = itemRepository.getAmountEntitiesByStatus(connection, itemStatus);
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

    private ItemPagination getPaginationFromDTO(ItemPaginationDTO paginationDTO) {
        ItemPagination pagination = new ItemPagination();
        pagination.setCurrentPage(paginationDTO.getCurrentPage());
        pagination.setItemsPerPage(paginationDTO.getItemsPerPage());
        return pagination;
    }

}
