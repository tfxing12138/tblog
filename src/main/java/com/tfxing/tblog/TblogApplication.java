package com.tfxing.tblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TblogApplication.class, args);

        log.info("启动成功");
    }

}
