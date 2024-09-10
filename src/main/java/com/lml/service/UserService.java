package com.lml.service;

import com.lml.pojo.User;

public interface UserService {

    User getByUserName(String username);

    void register(String username,String password);

    void update(User user);

    void updateAvater(String avatarUrl);
}
