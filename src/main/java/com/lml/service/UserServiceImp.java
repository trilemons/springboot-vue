package com.lml.service;

import com.lml.mapper.UserMapper;
import com.lml.pojo.User;
import com.lml.utils.Md5Util;
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
        String md5password = Md5Util.getMD5String(password);
        userMapper.save(username,md5password);
    }
}
