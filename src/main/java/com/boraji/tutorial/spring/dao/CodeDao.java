package com.boraji.tutorial.spring.dao;

import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.User;

import java.util.Optional;

public interface CodeDao {

    void addCode(Code code);

    Optional<Code> getLastCodeForUser(User user);
}
