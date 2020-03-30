package com.yangzhichao.yzclib.executor.async;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/1/19
 * @class <code>SyncExecutor</code>
 * @see
 * @since JDK1.8
 */
@Slf4j
public class SyncExecutor {

    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(6);


    private void say() {
        log.info("come in ！！！！！");
    }


    public static void main(String[] args) throws InterruptedException {
        SyncExecutor syncExecutor = new SyncExecutor();


        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
//        syncExecutor.threadPoolExecutor.submit(futureTask)


        //使用异步线程执行任务
        syncExecutor.threadPoolExecutor.execute(() -> {
            log.info("异步处理任务信息");
        });
        //使用函数式接口调用方法
        FunctionInterface lambda = syncExecutor::say;
        lambda.lambdaFunction();
        while (syncExecutor.threadPoolExecutor.getActiveCount() > 0) {
            Thread.sleep(5000);
        }
        syncExecutor.threadPoolExecutor.shutdown();

        try {
            byte[] a = "fasdf".getBytes(StandardCharsets.UTF_8.displayName());
            new String(a);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }
}