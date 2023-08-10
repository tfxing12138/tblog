package com.tfxing.tblog.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class UserMapper {

    public String list() {
        return "hello";
    }
}
