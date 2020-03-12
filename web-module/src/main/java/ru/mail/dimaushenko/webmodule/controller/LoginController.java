package ru.mail.dimaushenko.webmodule.controller;

import java.lang.invoke.MethodHandles;
import java.security.Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.mail.dimaushenko.service.UserService;

@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String welcomePage(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
