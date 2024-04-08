package com.integration.camel.sample.service;

import com.integration.camel.sample.mapper.InputCsvMapper;
import com.integration.camel.sample.mapper.UserMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MyBatisBatchInsertService implements BatchInsertService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional
    public void insertBatch(List<InputCsvMapper> users) {

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            for (InputCsvMapper user : users) {
                mapper.insertUser(user);
            }
            sqlSession.commit();
        }
    }
}