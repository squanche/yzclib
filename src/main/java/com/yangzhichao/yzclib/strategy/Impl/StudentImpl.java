package com.yangzhichao.yzclib.strategy.Impl;


import com.yangzhichao.yzclib.strategy.annotation.WorkAnnotation;
import com.yangzhichao.yzclib.strategy.enums.TypeEnum;

@WorkAnnotation(type = TypeEnum.STUDENT)
public class StudentImpl extends AbstractWork {
    @Override
    public void goWork() {
        System.out.println("学生去上课");

    }
}
