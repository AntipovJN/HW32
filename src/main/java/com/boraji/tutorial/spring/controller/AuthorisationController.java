package com.boraji.tutorial.spring.controller;

import java.util.Optional;

import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.UserService;
import com.boraji.tutorial.spring.utils.SHA256StringHashUtil;
import com.boraji.tutorial.spring.utils.SaltGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.security.auth.login.LoginException;

/**
 * @author imssbora
 */
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

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginView() throws LoginException {
        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(@RequestParam String password,
                        @RequestParam String email,
                        @SessionAttribute("user") User user,
                        Model model) throws LoginException {
        try {
            Optional<User> optionalUser = userService.getByEmail(email);
            if (optionalUser.isPresent()) {
                if(optionalUser.get().getPassword().equals(
                        SHA256StringHashUtil.getSha256(SaltGeneratorUtil.saltPassword(
                                password, optionalUser.get().getSalt()))))
                user.setId(optionalUser.get().getId());
                user.setEmail(optionalUser.get().getEmail());
                user.setPassword(optionalUser.get().getPassword());
                user.setRole(optionalUser.get().getRole());
                user.setSalt(optionalUser.get().getSalt());
                return "redirect:/products/store";
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid login or password");
            return "index";
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
        String salt = SaltGeneratorUtil.getSalt();

        try {
            userService.addUser(email, password, repeatPassword, role, salt);
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

    @RequestMapping(value = "/exit")
    public String exit(@ModelAttribute("user") User user) {
        user = new User();
        return "redirect:/login";
    }
}
