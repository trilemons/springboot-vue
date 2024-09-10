package com.lml.controller;

import com.lml.pojo.Result;
import com.lml.pojo.User;
import com.lml.service.UserService;
import com.lml.utils.JwtUtil;
import com.lml.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$")String username,@Pattern(regexp = "^\\S{5,16}$")String password){
        User user = userService.getByUserName(username);
        if (user==null){
            return Result.error("该账号不存在，请先注册");
        }

        if(Md5Util.getMD5String(password).equals(user.getPassword())){
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }


}
