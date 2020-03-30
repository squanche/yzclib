package com.yangzhichao.yzclib.juc.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class CountDownLanchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(7);


        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName()+"-- wancheng");

            }).start();
        }
        countDownLatch.await();
        System.out.println("all done");


    }


}
