package com.tfxing.tblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfxing.tblog.entity.ResponseInfo;
import com.tfxing.tblog.entity.User;
import com.tfxing.tblog.mapper.UserMapper;
import com.tfxing.tblog.service.UserService;
import com.tfxing.tblog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseInfo login(@RequestParam(value = "email") String email, @RequestParam(value = "passWord") String passWord) {
        //包装token
        User user = new User(null,email,passWord);
        userService.validUser(user);

        String token= TokenUtils.sign(user);

        HashMap<String,String> hs=new HashMap<>();
        hs.put("token",token);

        return ResponseInfo.<HashMap<String,String>>success(hs);
    }

    @PostMapping("/register")
    public ResponseInfo<Long> register(@Validated @RequestBody User user) throws Exception{
        Long id = userService.register(user);

        return ResponseInfo.success(id);
    }

    @PostMapping("/remove")
    public ResponseInfo remove(@RequestParam Long id) {
        userService.remove(id);

        return ResponseInfo.success();
    }

    @GetMapping("/list")
    public ResponseInfo<List<User>> list() {
        List<User> list = userService.list();
        return ResponseInfo.<List<User>> success(list);
    }
}
