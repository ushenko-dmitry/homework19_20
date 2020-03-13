package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.ItemRepository;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_ITEM_ID;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_ITEM_NAME;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_ITEM_STATUS;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemStatus;
import ru.mail.dimaushenko.repository.properties.RequestProperties;

@Repository
public class ItemRepositoryImpl extends GeneralRepositoryImpl<Item> implements ItemRepository {

    private final RequestProperties requestProperties;

    public ItemRepositoryImpl(
            RequestProperties requestProperties
    ) {
        this.requestProperties = requestProperties;
    }

    @Override
    public Item addEntity(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestInsertItem())) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getStatus().name());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        item.setId((long) resultSet.getInt(1));
                        return item;
                    } else {
                        throw new SQLException("Add `item` in DB was failed, no generated id");
                    }
                }
            } else {
                throw new SQLException("Add `item` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public List<Item> getAllEntities(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectAllItems())) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public Item getEntityById(Connection connection, Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectItemById())) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Item item = null;
                if (resultSet.next()) {
                    item = getItem(resultSet);
                }
                return item;
            }
        }
    }

    @Override
    public List<Item> getEntityByItemStatus(Connection connection, ItemStatus itemStatus) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectItemByStatus())) {
            preparedStatement.setString(1, itemStatus.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Item> items = new ArrayList<>();
                while (resultSet.next()) {
                    Item item = getItem(resultSet);
                    items.add(item);
                }
                return items;
            }
        }
    }

    @Override
    public Integer getAmountEntities(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestGetAmountItems())) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Integer amountDocuments = null;
                if (resultSet.next()) {
                    amountDocuments = resultSet.getInt(1);
                }
                return amountDocuments;
            }
        }
    }

    @Override
    public void updateEntity(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestUpdateItem())) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getStatus().name());
            preparedStatement.setLong(3, item.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update `item` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public void removeEntity(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestDeleteItem())) {
            preparedStatement.setLong(1, item.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Remove `item` in DB was failed, no affected rows");
            }
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId((long) resultSet.getInt(COLUMN_ITEM_ID));
        item.setName(resultSet.getString(COLUMN_ITEM_NAME));
        item.setStatus(ItemStatus.valueOf(resultSet.getString(COLUMN_ITEM_STATUS)));
        return item;
    }
}
