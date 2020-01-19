package com.yangzhichao.yzclib.executor.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/1/19
 * @class <code>SyncExecutor</code>
 * @see
 * @since JDK1.8
 */
public class SyncExecutor {

    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);



    private void say() {
        System.out.println("come in ！！！！！");
    }


    public static void main(String[] args) {
        SyncExecutor syncExecutor = new SyncExecutor();

        syncExecutor.threadPoolExecutor.execute(() -> {
            System.out.println("异步处理任务信息");
        });

        Lambda lambda = syncExecutor::say;
        lambda.lambdaFunction();


    }


}