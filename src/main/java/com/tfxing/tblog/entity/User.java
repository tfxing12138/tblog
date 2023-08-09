package com.tfxing.tblog.entity;

import lombok.Data;

@Data
public class User {

    private String userName;

    private String passWord;

    public User() {}

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
}
