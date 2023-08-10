package com.tfxing.tblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tfxing.tblog.mapper")
public class TblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TblogApplication.class, args);
    }

}
