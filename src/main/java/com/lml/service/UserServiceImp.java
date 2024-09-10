package com.lml.service;

import com.lml.mapper.UserMapper;
import com.lml.pojo.User;
import com.lml.utils.Md5Util;
import com.lml.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void register(String username, String password) {
        String md5password = Md5Util.getMD5String(password);
        userMapper.save(username,md5password);
    }

    @Override
    public void updatePwd(String newPwd) {
        String password = Md5Util.getMD5String(newPwd);
        Map claims = (Map) ThreadLocalUtil.get();
        int id = (int) claims.get("id");
        userMapper.updatePwd(password,id);
    }

    @Override
    public void updateAvater(String avatarUrl) {
        Map claims = (Map) ThreadLocalUtil.get();
        int id = (int) claims.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }
}
