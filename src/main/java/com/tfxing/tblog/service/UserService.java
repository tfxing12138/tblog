package com.tfxing.tblog.service;

import com.tfxing.tblog.entity.User;

import java.util.List;

public interface UserService {
    
    List<User> list();

    Long register(User user);

    void validUser(User user);

    void remove(Long id);
}
