package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemStatus;

public interface ItemRepository extends GeneralRepository<Item> {

    List<Item> getEntityByItemStatus(Connection connection, ItemStatus itemStatus) throws SQLException;

}
