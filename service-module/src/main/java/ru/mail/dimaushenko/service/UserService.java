package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.UserDTO;

public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    Integer getAmountUsers();

    boolean updateUser(UserDTO userDTO);

    boolean removeUser(UserDTO userDTO);

    public UserDTO getUserByUsername(String username);

}
