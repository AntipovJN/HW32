package com.boraji.tutorial.spring.service;

import com.boraji.tutorial.spring.entity.User;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(String email, String password, String passwordAgain, String role, String salt)
            throws LoginException;

    void updateUser(Long id, String email, String password, String passwordAgain)
            throws LoginException;

    void removeUser(Long id);

    List<User> getAll();

    Optional<User> getByEmail(String email);

    Optional<User> getById(Long id);
}
