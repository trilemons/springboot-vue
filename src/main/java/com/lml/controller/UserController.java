package com.lml.controller;

import com.lml.pojo.Result;
import com.lml.pojo.User;
import com.lml.service.UserService;
import com.lml.utils.JwtUtil;
import com.lml.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            //给令牌加入部分不敏感用户个人信息
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            //获取令牌
            String token = JwtUtil.genToken(claims);
            //将令牌信息返回
            return Result.success(token);        }

        return Result.error("密码错误");
    }

    @GetMapping("/detail")
    public Result<User> detail(@RequestHeader("authorization") String token){
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String username = (String) claims.get("username");

        User user = userService.getByUserName(username);
        return Result.success(user);
    }
}
