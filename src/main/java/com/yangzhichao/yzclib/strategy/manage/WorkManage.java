package com.yangzhichao.yzclib.strategy.manage;

import cn.hutool.core.lang.ClassScanner;
import com.yangzhichao.yzclib.strategy.annotation.WorkAnnotation;
import com.yangzhichao.yzclib.strategy.content.WorkContent;
import com.yangzhichao.yzclib.strategy.enums.TypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WorkManage implements BeanFactoryPostProcessor, ApplicationContextAware {

    private String path = "com.yangzhichao.yzclib.strategy";

    @Autowired
    private ApplicationContext applicationContexts;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<TypeEnum, Class> map = new HashMap();
        ClassScanner.scanPackageByAnnotation(path, WorkAnnotation.class).forEach(a -> {
            map.put(a.getAnnotation(WorkAnnotation.class).type(), a);
        });
        WorkContent workContent = new WorkContent(applicationContexts, map);
        configurableListableBeanFactory.registerSingleton(WorkAnnotation.class.getName(), workContent);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContexts = applicationContext;

    }
}
