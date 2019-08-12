package com.boraji.tutorial.spring.controller;

import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users")
    public String usersPageView(Model model) {
            model.addAttribute("users", userService.getAll());
            return "users";
    }

    @RequestMapping(value = "/users/remove", method = RequestMethod.GET)
    public String removeUser(@RequestParam String id) {
            userService.removeUser(Long.valueOf(id));
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.GET)
    public String editUserPageView(Model model, @RequestParam String id) {
            Optional<User> optionalUser = userService.getById(Long.valueOf(id));
            if (optionalUser.isPresent()) {
                model.addAttribute("id", id);
                model.addAttribute("email", optionalUser.get().getEmail());
                return "edit_user";
        }
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String editUser(
                           @RequestParam String id,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           Model model) {
            try {
                userService.updateUser(Long.valueOf(id), email, password, repeatPassword);
            } catch (LoginException e) {
                model.addAttribute("error", e.getMessage());
                return "edit_user";
        }
        return "redirect:/users";
    }
}
