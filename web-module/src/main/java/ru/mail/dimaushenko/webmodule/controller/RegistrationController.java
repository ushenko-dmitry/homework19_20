package ru.mail.dimaushenko.webmodule.controller;

import java.lang.invoke.MethodHandles;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mail.dimaushenko.service.UserService;
import ru.mail.dimaushenko.service.model.UserDTO;
import ru.mail.dimaushenko.webmodule.controller.model.UserForm;
import ru.mail.dimaushenko.service.model.UserRoleDTO;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(
            @ModelAttribute(name = "userForm") UserForm userForm,
            Model model
    ) {
        model.addAttribute("passwordError", null);
        return "registration";
    }

    @PostMapping
    public String addUser(
            @Valid @ModelAttribute(name = "userForm") UserForm userForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                logger.error(error.getDefaultMessage());
            }
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "registration";
        }
        UserDTO userDTO = getUser(userForm);
        userDTO = userService.addUser(userDTO);
        if (userDTO == null) {
            model.addAttribute("usernameError", "Username already exists");
            return "registration";
        }
        return "redirect:/login";
    }

    private UserDTO getUser(UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userForm.getUsername());
        userDTO.setPassword(userForm.getPassword());

        String userRole = userForm.getRole().toUpperCase();
        userDTO.setRole(UserRoleDTO.valueOf(userRole));
        return userDTO;
    }

}
