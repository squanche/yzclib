package com.yangzhichao.yzclib.zookeeper.curator.lock;

import com.yangzhichao.yzclib.YzclibApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = YzclibApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class CuratorDistrLockTest {

    @Autowired
    private CuratorDistrLock distrLock;


    @Test
    public void test() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                distrLock.lock();
                System.out.println("开始处理业务逻辑");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    distrLock.unlock();
                }

            }).start();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}