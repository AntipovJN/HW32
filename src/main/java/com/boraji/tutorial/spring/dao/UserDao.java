package com.boraji.tutorial.spring.dao;

import com.boraji.tutorial.spring.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void addUser(User user);

    List<User> getAll();

    Optional<User> getByEmail(String email);

    Optional<User> getById(Long id);

    void updateUser(User user);

    void removeUser(User user);
}
