package com.yangzhichao.yzclib.thread.threadLocal;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.UUID;

public class ThreadTest implements Runnable {


  public static final ThreadLocal<String> s1 = ThreadLocal.withInitial(() -> {
        return  new String();
    });
    public static final ThreadLocal<String> s2 = ThreadLocal.withInitial(() -> {
        return  new String();
    });

    @Override
    public void run() {
        s1.set(UUID.randomUUID().toString());
        System.out.println(Thread.currentThread().getName()+"=>"+s1.get());



    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new ThreadTest()).start();

        }
    }
}
