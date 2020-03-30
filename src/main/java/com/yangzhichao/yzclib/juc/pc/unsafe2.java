package com.yangzhichao.yzclib.juc.pc;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class unsafe2 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> integerFutureTask = new FutureTask<>(new myThread());

        new Thread(integerFutureTask, "A").start();
        Integer integer = integerFutureTask.get();
        System.out.println(integer);

    }
}

class myThread implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println("12312");
        return 1024;
    }
}

