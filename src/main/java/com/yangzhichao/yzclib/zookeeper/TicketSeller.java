package com.yangzhichao.yzclib.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;

import java.nio.channels.DatagramChannel;

/**
 * @author yangzhichao<yangzhichao @ cecdat.com>
 * @version v0.1 2020/2/10
 * @class <code>TicketSeller</code>
 * @see
 * @since JDK1.8
 */
@Slf4j
public class TicketSeller {


    private void sell() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "开始卖票");
        Thread.sleep(5 * 1000);
        log.info(Thread.currentThread().getName() + "卖票结束");
    }

    private void sellByLock() throws KeeperException, InterruptedException {
        DistributedLock distributedLock = new DistributedLock();
        distributedLock.createLock();
        distributedLock.attemptLock();
        sell();
        distributedLock.releaseLock();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                TicketSeller ticketSeller = new TicketSeller();
                try {
                    ticketSeller.sellByLock();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }


    }
}


