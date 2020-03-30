package com.yangzhichao.yzclib.chain;

import java.util.Objects;

public abstract class Student {
    private Student humanone;

    public void setHuman(Student h) {
        this.humanone = h;
    }


    public abstract boolean report(String name);


    public void pick(String name) {
        if (report(name)) {
            System.out.println(name+"点名开始");
            humanSay();
            System.out.println(name+"点名结束");
            return;
        }
        if (Objects.nonNull(humanone)) {
            humanone.pick(name);
        } else {
            System.out.println("没有这个人");
        }

    }


    public abstract void humanSay();



}
