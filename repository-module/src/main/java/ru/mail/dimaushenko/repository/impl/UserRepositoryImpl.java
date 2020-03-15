package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.UserRepository;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_USER_ID;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_USER_PASSWORD;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_USER_ROLE;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_USER_USERNAME;
import static ru.mail.dimaushenko.repository.constants.SQLColumnName.COLUMN_USER_UUID;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.repository.model.UserRole;
import ru.mail.dimaushenko.repository.properties.RequestProperties;

@Repository
public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private final RequestProperties requestProperties;

    public UserRepositoryImpl(
            RequestProperties requestProperties
    ) {
        this.requestProperties = requestProperties;
    }

    @Override
    public User addEntity(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestInsertUser())) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setString(4, user.getUuid());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        user.setId((long) resultSet.getInt(1));
                        return user;
                    } else {
                        throw new SQLException("Add `user` in DB was failed, no generated id");
                    }
                }
            } else {
                throw new SQLException("Add `user` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public List<User> getAllEntities(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectAllUsers())) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    User user = getUser(resultSet);
                    users.add(user);
                }
                return users;
            }
        }
    }

    @Override
    public User getEntityByUUID(Connection connection, String uuid) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectUserByUUID())) {
            preparedStatement.setString(1, uuid);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    user = getUser(resultSet);
                }
                return user;
            }
        }
    }

    @Override
    public User getEntityByUsername(Connection connection, String username) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectUserByUsername())) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    user = getUser(resultSet);
                }
                return user;
            }
        }
    }

    @Override
    public Integer getAmountEntities(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestGetAmountUsers())) {
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
    public void updateEntity(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestUpdateUser())) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setString(4, user.getUuid());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update `user` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public void removeEntity(Connection connection, User user) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestDeleteUser())) {
            preparedStatement.setString(1, user.getUuid());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Remove `user` in DB was failed, no affected rows");
            }
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId((long) resultSet.getInt(COLUMN_USER_ID));
        user.setUuid(resultSet.getString(COLUMN_USER_UUID));
        user.setUsername(resultSet.getString(COLUMN_USER_USERNAME));
        user.setPassword(resultSet.getString(COLUMN_USER_PASSWORD));
        user.setRole(UserRole.valueOf(resultSet.getString(COLUMN_USER_ROLE)));
        return user;
    }

}
