package com.lml.service;

import com.lml.mapper.UserMapper;
import com.lml.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        userMapper.save(username,password);
    }
}
