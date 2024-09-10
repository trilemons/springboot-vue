package com.lml.mapper;

import com.lml.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    //以下是一种pojo类映射数据库表的列字段的方式
//    @Results({
//            @Result(property = "createTime",column = "create_time"),
//            @Result(property = "updateTime", column = "update_time")}
//    )
    User selectByUserName(String username);

    @Insert("insert into user(username,password,create_time,update_time)"+
            "values (#{username},#{password},now(),now())")
    void save(String username,String password);


    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    void update(User user);

    //这里的方法不能重载
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, int id);
}
