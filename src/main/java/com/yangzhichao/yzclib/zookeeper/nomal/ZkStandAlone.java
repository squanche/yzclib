package com.yangzhichao.yzclib.zookeeper.nomal;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Objects;
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


            String kvm = "localhost:2181";
            String path = "/zktest";
            zk = new ZooKeeper(kvm, 400000, (WatchedEvent watchedEvent) -> {
                if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {

                    try {
                        if(Objects.isNull(zk.exists(path,false))){
                            zk.create(path, "yanghzichao".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

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
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}