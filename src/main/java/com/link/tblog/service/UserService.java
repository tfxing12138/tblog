package com.link.tblog.service;

import com.link.tblog.entity.User;

import java.util.List;

public interface UserService {
    
    List<User> list();

    Long register(User user) throws Exception;

    void validUser(User user) throws Exception;

    void remove(Long id);

    User getUserById(Long id);
}
