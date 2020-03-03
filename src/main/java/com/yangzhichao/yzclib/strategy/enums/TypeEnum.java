package com.yangzhichao.yzclib.strategy.enums;

public enum TypeEnum {

    STUDENT("学生"),
    TEACHER("教师");


    TypeEnum(String role) {
        this.role = role;
    }


    private String role;
}
