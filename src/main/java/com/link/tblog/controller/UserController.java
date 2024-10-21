package com.link.tblog.controller;

import com.link.tblog.entity.ResponseInfo;
import com.link.tblog.entity.User;
import com.link.tblog.service.UserService;
import com.link.tblog.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @ExceptionHandler(RuntimeException.class)
    public ResponseInfo login(@RequestParam(value = "email") String email, @RequestParam(value = "passWord") String passWord) throws Exception {
        //包装token
        User user = new User(null,email,passWord);
//        userService.validUser(user);

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

    @GetMapping("/getUserById")
    public ResponseInfo<User> getUserById(Long id) {
        return ResponseInfo.<User> success(userService.getUserById(id));
    }
}
