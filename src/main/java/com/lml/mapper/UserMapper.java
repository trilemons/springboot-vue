package com.lml.mapper;

import com.lml.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    //以下是一种pojo类映射数据库表的列字段的方式
//    @Results({
//            @Result(property = "createTime",column = "create_time"),
//            @Result(property = "updateTime", column = "update_time")}
//    )
    User selectByUserName(String username);

    @Select("insert into user(username,password,create_time,update_time)"+
            "values (#{username},#{password},now(),now())")
    void save(String username,String password);

}
