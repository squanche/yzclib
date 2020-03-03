package com.yangzhichao.yzclib;

import com.yangzhichao.yzclib.strategy.content.WorkContent;
import com.yangzhichao.yzclib.strategy.enums.TypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes =YzclibApplication.class ,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class YzclibApplicationTests {

    @Autowired
    private WorkContent workContent;

    @Test
    void contextLoads() {
        workContent.getWork(TypeEnum.STUDENT).goWork();
        workContent.getWork(TypeEnum.TEACHER).goWork();




    }

}
