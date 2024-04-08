package com.integration.camel.sample.service;

import com.integration.camel.sample.mapper.InputCsvMapper;

import java.util.List;

public interface BatchInsertService {
    void insertBatch(List<InputCsvMapper> users);
}