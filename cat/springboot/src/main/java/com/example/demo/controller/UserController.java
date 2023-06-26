package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;

    @PostMapping("/loginusers")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()).eq(User::getPassword,user.getPassword()));
        if(res == null)
        {
            return Result.error("-1","用户密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(res != null)
        {
            return Result.error("-1","该用户名已被使用，请重新输入");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @RequestMapping("/show_userinfo")
    public Result<?> show_userinfo(@RequestParam("username") String username){
        userMapper.getUserInfo(username);
        return Result.success(userMapper.getUserInfo(username));
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success(user);
    }
}
