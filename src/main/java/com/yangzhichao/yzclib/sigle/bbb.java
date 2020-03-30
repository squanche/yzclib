package com.yangzhichao.yzclib.sigle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum bbb {

    sigle;
    public void dosomethin() {
        System.out.println("aaaaaa");
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

//        Constructor<bbb> declaredConstructor = bbb.class.getDeclaredConstructor(String.class,int.class);
//        declaredConstructor.setAccessible(true);
//        declaredConstructor.newInstance();
        System.out.println("dfsdf".hashCode());


    }


}
