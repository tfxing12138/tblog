package com.tfxing.tblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfxing.tblog.entity.User;
import com.tfxing.tblog.mapper.UserMapper;
import com.tfxing.tblog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public HashMap login(@RequestParam(value = "userName") String userName, @RequestParam(value = "passWord") String passWord) {
        //包装token
        User user = new User(userName,passWord);
        String token= TokenUtils.sign(user);

        HashMap<String,Object> hs=new HashMap<>();
        hs.put("token",token);

        return hs;
    }

    @GetMapping("/list")
    public List<User> list() {
        String list = userMapper.list();
        return Arrays.asList(new User("link","link12"), new User(list,""));
    }
}
