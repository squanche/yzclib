package com.yangzhichao.yzclib.juc.pc;

public class oldPC {
    int num = 0;

    public synchronized void inscrease() throws InterruptedException {
        while (num != 0) {
            wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        notifyAll();


    }

    public synchronized void decrease() throws InterruptedException {
        while (num == 0) {
            wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "<=" + num);
        notifyAll();

    }

    public static void main(String[] args) {

        oldPC o=new oldPC();
        new Thread(() -> {
            for (int i=0; i < 10; i++) {
                try {
                    o.inscrease();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "A").start();

        new Thread(() -> {
            for (int i=0; i < 10; i++) {
                try {
                    o.inscrease();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "C").start();


        new Thread(() -> {
            for (int i=0; i < 10; i++) {
                try {
                    o.decrease();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "B").start();

        new Thread(() -> {
            for (int i=0; i < 10; i++) {
                try {
                    o.decrease();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }, "D").start();

    }


}
