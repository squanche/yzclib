package com.yangzhichao.yzclib.juc.stream;

import com.yangzhichao.yzclib.juc.demo;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class test1 {
    static AtomicStampedReference<demo> atomicStampedReference = new AtomicStampedReference<>(new demo(), 1);


    public static void main(String[] args) {


    }
}
