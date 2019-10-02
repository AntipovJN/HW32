package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.security.auth.login.LoginException;
import java.util.Objects;

@Controller
@SessionAttributes({"user"})
public class AuthorisationController {

    private final UserService userService;

    @Autowired
    public AuthorisationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User userSession(User user) {
        return user;
    }

    @RequestMapping("/login")
    public String login(@AuthenticationPrincipal User user) {
        if (Objects.isNull(user)) {
            return "index";
        } else {
            if (user.getRole().equals("ROLE_ADMIN")) {
                return "redirect:/admin/users";
            } else {
                return "redirect:/user/store";
            }
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerView() {
        return "register";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           @RequestParam String role) {
        try {
            userService.addUser(email, password, repeatPassword, role);
            return "redirect:/login";
        } catch (LoginException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (IllegalArgumentException e) {
            model.addAttribute("email", email);
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
