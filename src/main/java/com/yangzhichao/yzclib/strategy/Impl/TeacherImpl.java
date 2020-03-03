package com.yangzhichao.yzclib.strategy.Impl;

import com.yangzhichao.yzclib.strategy.annotation.WorkAnnotation;
import com.yangzhichao.yzclib.strategy.enums.TypeEnum;

@WorkAnnotation(type = TypeEnum.TEACHER)
public class TeacherImpl extends AbstractWork {
    @Override
    public void goWork() {
        System.out.println("老师去上课");
    }
}
