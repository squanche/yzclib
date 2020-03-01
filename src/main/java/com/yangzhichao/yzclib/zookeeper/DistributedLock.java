package com.yangzhichao.yzclib.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/2/9
 * @class <code>DistributedLock</code>
 * @see
 * @since JDK1.8
 */
@Slf4j
public class DistributedLock {
    private ZooKeeper zkClient;
    private final String LOCK_ROOT_PATH = "/Locks";
    private final String LOCK_NODE_NAME = "Lock_";
    private String lockPath;
    CountDownLatch countDownLatch = new CountDownLatch(1);


    public DistributedLock() {
        try {
            zkClient = new ZooKeeper("192.168.79.131:2181", 1000000, new MyWatch());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class MyWatch implements Watcher {
        @Override
        public void process(WatchedEvent event) {
            try {
                if (event.getState() == Watcher.Event.KeeperState.Expired) {
                    zkClient = new ZooKeeper("192.168.79.131:2181", 1000000, new MyWatch());
                } else if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent event) {
            log.info(event.getPath() + " 前锁释放");
            synchronized (this) {
                notifyAll();
            }

        }
    };


    /**
     * 创建节点信息
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void createLock() throws KeeperException, InterruptedException {
        //获取默认路径地址，改成有外部传入
        countDownLatch.await();
        Stat stat = zkClient.exists(LOCK_ROOT_PATH, false);
        if (Objects.isNull(stat)) {
            zkClient.create(LOCK_ROOT_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        String lockPath = zkClient.create(LOCK_ROOT_PATH + "/" + LOCK_NODE_NAME, Thread.currentThread().getName().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("锁被{}创建成功", Thread.currentThread().getName());
        this.lockPath = lockPath;
    }


    /**
     * 获取锁
     */
    public void attemptLock() throws KeeperException, InterruptedException {
        List<String> listPaths;

        listPaths = zkClient.getChildren(LOCK_ROOT_PATH, false);
        Collections.sort(listPaths);
        int index = listPaths.indexOf(lockPath.substring(LOCK_ROOT_PATH.length() + 1));
        if (index == 0) {
            log.info(Thread.currentThread().getName() + "获得了锁");
            return;
        } else {
            String preLockPath = listPaths.get(index - 1);
            Stat stat = zkClient.exists(LOCK_ROOT_PATH + "/" + preLockPath, watcher);
            if (stat == null) {
                attemptLock();
            } else {
                log.info(" 等待前锁释放，prelocakPath：" + preLockPath);
                synchronized (watcher) {
                    watcher.wait();
                }
                attemptLock();
            }
        }


    }

    /**
     * 释放锁
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void releaseLock() throws KeeperException, InterruptedException {
        zkClient.delete(lockPath, -1);
        zkClient.close();
        log.info(" 锁释放：" + lockPath);
    }


}