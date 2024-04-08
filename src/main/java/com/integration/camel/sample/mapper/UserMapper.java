package com.integration.camel.sample.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select({
            "select id, firstname, lastname, email, email2, profession from users order by id"
    })
    List<InputCsvMapper> selectAll();

    @Select({
            "select id, firstname, lastname, email, email2, profession from users where id = #{value}"
    })
    InputCsvMapper selectById(Integer id);

    @Insert({
            "insert into users (firstname, lastname,fullname, email, email2, profession) values(#{firstName}, #{lastName},#{fullName},#{email}, #{email2}, #{profession})"
    })
    int insertUser(InputCsvMapper user);
}