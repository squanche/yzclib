package com.yangzhichao.yzclib.chain;

public class StudentBoss extends Student {

    private String namea = "BOSS";

    @Override
    public boolean report(String name) {
        if (!namea.equals(name)){
            System.out.println(namea+"=>嘿嘿没有点名到我");
        }
        return namea.equals(name);


    }

    @Override
    public void humanSay() {
        System.out.println(namea + "：我被抓了");
    }
}
