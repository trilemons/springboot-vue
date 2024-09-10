package com.lml.mapper;

import com.lml.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User selectByUserName(String username);

    @Select("insert into user(username,password,create_time,update_time)"+
            "values (#{username},#{password},now(),now())")
    void save(String username,String password);

}
