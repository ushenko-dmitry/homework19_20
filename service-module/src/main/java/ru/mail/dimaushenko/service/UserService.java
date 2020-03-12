package ru.mail.dimaushenko.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.mail.dimaushenko.service.model.UserDTO;

public interface UserService extends UserDetailsService{

    UserDTO addUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);
    
    UserDTO getUserByUsername(String username);

    Integer getAmountUsers();

    boolean updateUser(UserDTO userDTO);

    boolean removeUser(UserDTO userDTO);

}
