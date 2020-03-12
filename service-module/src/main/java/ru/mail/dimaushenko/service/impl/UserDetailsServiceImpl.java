package ru.mail.dimaushenko.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.AppUser;
import ru.mail.dimaushenko.service.model.UserDTO;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByUsername(username);
        if (userDTO == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new AppUser(userDTO);
    }

}
