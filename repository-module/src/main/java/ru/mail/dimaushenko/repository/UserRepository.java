package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import ru.mail.dimaushenko.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    public User getEntityByUsername(Connection connection, String username) throws SQLException;

}
