package com.yangzhichao.yzclib.juc;

import java.util.concurrent.TimeUnit;

public class demo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(getCPUCount());
        TimeUnit.SECONDS.sleep(1);
    }


    public static int getCPUCount() {


        return Runtime.getRuntime().availableProcessors();

    }
}
