package com.yangzhichao.yzclib.executor.VolatileTest;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/2/26
 * @class <code>volatileTest</code>
 * @see
 * @since JDK1.8
 */
@Slf4j
public class volatileTest {
    private volatile boolean flag = false;

    public void say() {
        try {
            if (flag) {
                log.info("并发量校验失败");
                return;
            }
            flag = true;
            log.info("正在处理中");
            return;
        } finally {
            flag = false;
        }

    }

    public static void main(String[] args) {
        volatileTest volatileTest = new volatileTest();
        for (int i = 0; i < 100; i++) {
            Runnable a = () -> {
                try {
                    Thread.sleep(new Random().nextInt(10)*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volatileTest.say();
            };
            new Thread(a).start();

        }

    }


}