package com.yangzhichao.yzclib.strategy.content;

import com.yangzhichao.yzclib.strategy.enums.TypeEnum;
import com.yangzhichao.yzclib.strategy.intf.Work;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WorkContent {

    public Map<TypeEnum, Class> map = new HashMap<>();
    public ApplicationContext applicationContext;


    public WorkContent(ApplicationContext applicationContext, Map<TypeEnum, Class> map) {

        this.map = map;
        this.applicationContext = applicationContext;

    }


    public Work getWork(TypeEnum typeEnum) {
        Class clazz = map.get(typeEnum);
        if (Objects.nonNull(clazz)) {
            return (Work) applicationContext.getBean(clazz);
        }
        return null;


    }


}
