package com.integration.camel.sample.service;

import com.integration.camel.sample.mapper.InputCsvMapper;

import java.util.List;

public interface UserService {

    List<InputCsvMapper> getAllUsers();
    InputCsvMapper getUser(Integer id);
    void insertUser(InputCsvMapper user);
}