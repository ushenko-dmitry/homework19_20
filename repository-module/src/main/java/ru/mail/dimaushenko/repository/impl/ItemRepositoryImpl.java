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
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_ITEM_UUID;
import ru.mail.dimaushenko.repository.model.Item;
import ru.mail.dimaushenko.repository.model.ItemPagination;
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
            preparedStatement.setString(3, item.getUuid());
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
    public List<Item> getLimitItems(Connection connection, ItemPagination pagination) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectLimitItems())) {
            Integer startDocument = pagination.getCurrentPage() * pagination.getItemsPerPage() - pagination.getItemsPerPage();
            preparedStatement.setInt(1, startDocument);
            preparedStatement.setInt(2, pagination.getItemsPerPage());
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
    public Item getEntityByUUID(Connection connection, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectItemByUUID())) {
            preparedStatement.setString(1, uuid);
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
    public List<Item> getLimitItemsByItemStatus(Connection connection, ItemStatus itemStatus, ItemPagination pagination) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectLimitItemsByStatus())) {
            Integer startDocument = pagination.getCurrentPage() * pagination.getItemsPerPage() - pagination.getItemsPerPage();
            preparedStatement.setString(1, itemStatus.name());
            preparedStatement.setInt(2, startDocument);
            preparedStatement.setInt(3, pagination.getItemsPerPage());
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
    public Integer getAmountEntitiesByStatus(Connection connection, ItemStatus itemStatus) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestGetAmountItemsByStatus())) {
            preparedStatement.setString(1, itemStatus.name());
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
            preparedStatement.setString(3, item.getUuid());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update `item` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public void removeEntity(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestDeleteItem())) {
            preparedStatement.setString(1, item.getUuid());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Remove `item` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public boolean isItemFound(Connection connection, Item item) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectItemByUUID())) {
            preparedStatement.setString(1, item.getUuid());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId((long) resultSet.getInt(COLUMN_ITEM_ID));
        item.setUuid(resultSet.getString(COLUMN_ITEM_UUID));
        item.setName(resultSet.getString(COLUMN_ITEM_NAME));
        item.setStatus(ItemStatus.valueOf(resultSet.getString(COLUMN_ITEM_STATUS)));
        return item;
    }

}
