package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.UserRepository;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.service.UserConvertService;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.UserDTO;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserRepository userRepository;
    private final UserConvertService userConvertService;

    public UserServiceImpl(UserRepository userRepository, UserConvertService userConvertService) {
        this.userRepository = userRepository;
        this.userConvertService = userConvertService;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userConvertService.getObjectFromDTO(userDTO);
                user = userRepository.addEntity(connection, user);
                userDTO = userConvertService.getDTOFromObject(user);
                connection.commit();
                return userDTO;
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
    public List<UserDTO> getAllUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> users = userRepository.getAllEntities(connection);
                List<UserDTO> userDTOs = userConvertService.getDTOFromObject(users);
                connection.commit();
                return userDTOs;
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
    public UserDTO getUserById(Long userId) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getEntityById(connection, userId);
                UserDTO userDTO = userConvertService.getDTOFromObject(user);
                connection.commit();
                return userDTO;
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
    public UserDTO getUserByUsername(String username) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.getEntityByUsername(connection, username);
                UserDTO userDTO = userConvertService.getDTOFromObject(user);
                connection.commit();
                return userDTO;
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
    public Integer getAmountUsers() {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Integer amountUsers = userRepository.getAmountEntities(connection);
                connection.commit();
                return amountUsers;
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
    public boolean updateUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userConvertService.getObjectFromDTO(userDTO);
                userRepository.updateEntity(connection, user);
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
    public boolean removeUser(UserDTO userDTO) {
        try (Connection connection = userRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userConvertService.getObjectFromDTO(userDTO);
                userRepository.removeEntity(connection, user);
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
