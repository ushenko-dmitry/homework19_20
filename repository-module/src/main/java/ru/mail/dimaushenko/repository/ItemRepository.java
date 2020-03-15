package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemPagination;
import ru.mail.dimaushenko.repository.model.ItemStatus;

public interface ItemRepository extends GeneralRepository<Item> {

    List<Item> getLimitItems(Connection connection, ItemPagination pagination) throws SQLException;

    List<Item> getLimitItemsByItemStatus(Connection connection, ItemStatus itemStatus, ItemPagination pagination) throws SQLException;

    List<Item> getEntityByItemStatus(Connection connection, ItemStatus itemStatus) throws SQLException;

    boolean isItemFound(Connection connection, Item item) throws SQLException;

    Integer getAmountEntitiesByStatus(Connection connection, ItemStatus itemStatus) throws SQLException;

}
