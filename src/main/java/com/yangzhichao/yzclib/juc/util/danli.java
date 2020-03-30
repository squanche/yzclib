package com.yangzhichao.yzclib.juc.util;

import com.sun.tools.classfile.ConstantPool;

import java.security.PublicKey;

public class danli {


    private static volatile CountDownLanchDemo countDownLanchDemo;


    private danli() {
    }


    public static CountDownLanchDemo getinst() {
        if (countDownLanchDemo == null) {
            synchronized (danli.class) {
                if (countDownLanchDemo == null) {
                    countDownLanchDemo = new CountDownLanchDemo();
                }

            }

        }
        return countDownLanchDemo;

    }


}
