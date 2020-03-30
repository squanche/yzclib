package com.yangzhichao.yzclib.juc.executers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test1 {

    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
         ExecutorService executorService = Executors.newCachedThreadPool();
       // ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(Runtime.getRuntime().availableProcessors());

        try {

            for (int i = 0; i < 100; i++) {
                final int temp = i;
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());

                });

            }
        } catch (Exception e) {
        } finally {
            executorService.shutdown();
        }


    }


}
