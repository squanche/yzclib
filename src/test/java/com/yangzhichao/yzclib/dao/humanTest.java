package com.yangzhichao.yzclib.dao;

import com.yangzhichao.yzclib.YzclibApplication;
import com.yangzhichao.yzclib.strategy.enums.TypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = YzclibApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class humanTest {

    @Autowired
    humanDao humandao;


    @Test
    void contextLoads() {
        System.out.println(humandao.getHumanlist());

    }
}
