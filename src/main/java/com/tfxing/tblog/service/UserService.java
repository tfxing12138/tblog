package com.tfxing.tblog.service;

import com.tfxing.tblog.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface UserService {
    
    List<User> list();

    Long register(User user) throws Exception;

    void validUser(User user) throws Exception;

    void remove(Long id);
}
