package com.yangzhichao.yzclib.strategy.Impl;

import com.yangzhichao.yzclib.strategy.intf.Work;

public abstract class AbstractWork implements Work {

    @Override
    public abstract void goWork();



    public void eat() {
        System.out.println("eat food");
    }

}
