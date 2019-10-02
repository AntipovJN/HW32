package com.boraji.tutorial.spring.service;

import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.User;

import java.util.Optional;

public interface CodeService {

    void addCode(Code code);

    Optional<Code> getLastCodeForUser(User user);
}
