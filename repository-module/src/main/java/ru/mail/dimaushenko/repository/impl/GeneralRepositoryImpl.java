package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mail.dimaushenko.repository.GeneralRepository;

public abstract class GeneralRepositoryImpl<T> implements GeneralRepository<T> {

    @Autowired
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
