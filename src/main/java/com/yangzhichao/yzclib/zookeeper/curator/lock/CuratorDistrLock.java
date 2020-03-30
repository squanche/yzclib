package com.yangzhichao.yzclib.zookeeper.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//@Component
public class CuratorDistrLock implements InitializingBean {

    //zk的连接地址
    private final static String ZOOKEEPER_ADRESS = "localhost:2181";

    private final static String LOCK_PATH = "/DISTR_LOCK";
    //zk的会话超时时间
    private final static int SESSION_TIMEOUT = 1000 * 1000;
    //zk的连接时间
    private final static int CONNECT_TIMEOUT = 1000 * 1000;


    InterProcessMutex lock = null;


    @Override
    public void afterPropertiesSet() throws Exception {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //获取连接客户端
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_ADRESS).connectionTimeoutMs(CONNECT_TIMEOUT)
                .sessionTimeoutMs(SESSION_TIMEOUT).retryPolicy(retryPolicy).build();
        client.start();
        lock = new InterProcessMutex(client, LOCK_PATH);
        System.out.println("zk client start successfully!");

    }

    public void lock() {
        try {
            if (lock.acquire(1000, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + "获得了锁");
            }

        } catch (Exception e) {
        }


    }


    public void unlock() {
        try {
            lock.release();
            System.out.println(Thread.currentThread().getName() + "释放了锁");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
