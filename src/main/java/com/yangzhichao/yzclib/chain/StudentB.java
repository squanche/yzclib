package com.yangzhichao.yzclib.chain;

public class StudentB extends Student {
    private String nameb = "B";

    @Override
    public boolean report(String name) {
        if (!nameb.equals(name)){
            System.out.println(nameb+"=>嘿嘿没有点名到我");
        }
        return nameb.equals(name);


    }

    @Override
    public void humanSay() {
        System.out.println(nameb + "：我被抓了");
    }

}
