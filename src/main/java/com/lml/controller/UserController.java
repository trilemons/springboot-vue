package com.lml.controller;

import com.lml.pojo.Result;
import com.lml.pojo.User;
import com.lml.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){

        User user = userService.getByUserName(username);

        if (user==null){
            userService.register(username,password);
            return Result.success();
        }
        else {
            return Result.error("用户已存在");
        }
    }


}
