package com.boraji.tutorial.spring.service.impl;

import com.boraji.tutorial.spring.dao.CodeDao;
import com.boraji.tutorial.spring.entity.Code;
import com.boraji.tutorial.spring.entity.User;
import com.boraji.tutorial.spring.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {

    private final CodeDao codeDao;

    @Autowired
    public CodeServiceImpl(CodeDao codeDao) {
        this.codeDao = codeDao;
    }

    @Transactional
    @Override
    public void addCode(Code code) {
        codeDao.addCode(code);
    }

    @Transactional
    @Override
    public Optional<Code> getLastCodeForUser(User user) {
        return codeDao.getLastCodeForUser(user);
    }
}
