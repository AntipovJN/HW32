package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.UserDao;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void addUser(String email, String password, String passwordAgain, String role, String salt)
            throws IllegalArgumentException, LoginException {
        validateUserData(email, password, passwordAgain);
        if ((getByEmail(email).isPresent())) {
            throw new LoginException("Try another login");
        }
        userDao.addUser(new User(email, password, role, salt));
    }

    @Transactional
    @Override
    public void updateUser(Long id, String newEmail, String newPassword, String newPasswordAgain)
            throws IllegalArgumentException, LoginException {
        validateUserData(newEmail, newPassword, newPasswordAgain);
        Optional<User> optionalUser = userDao.getByEmail(newEmail);
        if (optionalUser.isPresent()) {
            if (!optionalUser.get().getId().equals(id)) {
                log.error(String.format("Failed update user with id ='%s'", id));
                throw new LoginException("Use another email");
            }
        }
        optionalUser = userDao.getById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(newEmail);
            user.setPassword(newPassword);
            userDao.updateUser(user);
        }
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        if (userDao.getById(id).isPresent()) {
            userDao.removeUser(userDao.getById(id).get());
        } else
            log.error(String.format("Failed removing user with id ='%s'", id));
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public Optional<User> getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Transactional
    @Override
    public Optional<User> getById(Long id) {
        return userDao.getById(id);
    }

    private void validateUserData(String email, String password, String passwordAgain)
            throws IllegalArgumentException {
        if (Objects.isNull(email) || email.isEmpty()) {
            throw new IllegalArgumentException("You must use email for registration");
        }
        if (Objects.isNull(password) || Objects.isNull(passwordAgain)
                || password.isEmpty() || passwordAgain.isEmpty()) {
            throw new IllegalArgumentException("You must use password for registration");
        }
        if (!password.equals(passwordAgain)) {
            throw new IllegalArgumentException("Passwords not equals");
        }
    }
}
