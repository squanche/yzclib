package com.yangzhichao.yzclib.sigle;

import com.yangzhichao.yzclib.enums.TaskStatus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SigleTest {


    private SigleTest() {
        System.out.println("dan");
    }

    public static class Inner {

        private final static int age = 5;
        private static String name = new String("yzc");
        private final static SigleTest sigleTest = new SigleTest();

    }

    public static SigleTest getInstants() {
        return Inner.sigleTest;
    }

    public static String getName() {
        return Inner.name;
    }

    public static int getage() {
        return Inner.age;
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
  /*      Constructor<SigleTest> declaredConstructor = SigleTest.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        SigleTest sigleTest = declaredConstructor.newInstance();
        SigleTest sigleTest2 = declaredConstructor.newInstance();
        System.out.println(sigleTest);
        System.out.println(sigleTest2);*/

    }


}

