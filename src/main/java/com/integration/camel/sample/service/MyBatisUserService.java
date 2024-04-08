package com.integration.camel.sample.service;

import com.integration.camel.sample.mapper.InputCsvMapper;
import com.integration.camel.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MyBatisUserService implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public List<InputCsvMapper> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    @Transactional
    public void insertUser(InputCsvMapper user) {
        userMapper.insertUser(user);
    }

    @Override
    @Transactional
    public InputCsvMapper getUser(Integer id) {
        return userMapper.selectById(id);
    }
}
