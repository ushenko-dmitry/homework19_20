package ru.mail.dimaushenko.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.mail.dimaushenko.repository.model.User;
import ru.mail.dimaushenko.repository.model.UserRole;
import ru.mail.dimaushenko.service.UserConvertService;
import ru.mail.dimaushenko.service.model.UserDTO;
import ru.mail.dimaushenko.service.model.UserRoleDTO;

@Component
public class UserConvertServiceImpl implements UserConvertService {

    @Override
    public UserDTO getDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUuid(user.getUuid());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(getUserRoleDTOFromUserRole(user.getRole()));
        return userDTO;
    }

    @Override
    public List<UserDTO> getDTOFromObject(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = getDTOFromObject(user);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUuid(userDTO.getUuid());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(getUserRoleFromUserRoleDTO(userDTO.getRole()));
        return user;
    }

    @Override
    public List<User> getObjectFromDTO(List<UserDTO> userDTOs) {
        List<User> users = new ArrayList<>();
        for (UserDTO userDTO : userDTOs) {
            User user = getObjectFromDTO(userDTO);
            users.add(user);
        }
        return users;
    }

    private UserRoleDTO getUserRoleDTOFromUserRole(UserRole userRole) {
        if (userRole != null) {
            switch (userRole) {
                case ADMIN:
                    return UserRoleDTO.ADMIN;
                case USER:
                    return UserRoleDTO.USER;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    private UserRole getUserRoleFromUserRoleDTO(UserRoleDTO userRoleDTO) {
        if (userRoleDTO != null) {
            switch (userRoleDTO) {
                case ADMIN:
                    return UserRole.ADMIN;
                case USER:
                    return UserRole.USER;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}
