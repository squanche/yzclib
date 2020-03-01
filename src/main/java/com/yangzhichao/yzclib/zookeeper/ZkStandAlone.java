package com.yangzhichao.yzclib.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/1/20
 * @class <code>ZkStandAlone</code>
 * @see
 * @since JDK1.8
 */
public class ZkStandAlone {
    private static Stat stat = new Stat();
    private static ZooKeeper zk = null;


    public static void main(String[] args) throws IOException {

        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);


            String kvm = "192.168.79.131:2181";
            String path = "/zktest";
            zk = new ZooKeeper(kvm, 400000, (WatchedEvent watchedEvent) -> {
                if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    if (Watcher.Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                        countDownLatch.countDown();
                    } else if (watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
                        try {
                            System.out.println("配置已修改，新值为：" + new String(zk.getData(watchedEvent.getPath(), true, stat)));
                        } catch (Exception e) {
                        }
                    }
                }
            });
            countDownLatch.await();
            System.out.println(zk.getState());
            System.out.println(new String(zk.getData(path, true, stat)));
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }


}