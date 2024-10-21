package com.link.tblog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TblogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testTrs() {
        testTrs1();
    }

    @Transactional(rollbackFor = Exception.class)
    public void testTrs1() {
        testTrs2();
    }

    @Transactional(rollbackFor = Exception.class)
    public void testTrs2() {
        int i = 1 / 0;
    }

}
