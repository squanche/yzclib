package com.yangzhichao.yzclib.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZkStandAlone {
    //zk的连接地址
    private final static String ZOOKEEPER_ADRESS = "localhost:2181";
    //zk的会话超时时间
    private final static int SESSION_TIMEOUT = 1000 * 1000;
    //zk的连接时间
    private final static int CONNECT_TIMEOUT = 1000 * 1000;


    public static void main(String[] args) throws Exception {
        //重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //获取连接客户端
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_ADRESS).connectionTimeoutMs(CONNECT_TIMEOUT)
                .sessionTimeoutMs(SESSION_TIMEOUT).retryPolicy(retryPolicy).build();
        //开启连接
        client.start();
        System.out.println(client.getState());
        Stat stat = client.checkExists().forPath("/yzc");
        if (Objects.nonNull(stat)) {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/yzc");
        }
        client.create().withMode(CreateMode.PERSISTENT).forPath("/yzc", "yangzhichao".getBytes());

        //watch方法将使用线程池执行
        ExecutorService pool = Executors.newFixedThreadPool(2);

        final TreeCache treeCache = new TreeCache(client, "/yzc");
        treeCache.start();
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("================WATCH EVENT==================");
                if (Objects.nonNull(treeCacheEvent)) {
                    System.out.println("data change" + treeCacheEvent.getType());
                }

            }
        }, pool);

        Thread.sleep(Integer.MAX_VALUE);
        pool.shutdown();
        client.close();

    }


}
