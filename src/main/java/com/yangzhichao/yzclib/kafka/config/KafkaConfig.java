package com.yangzhichao.yzclib.kafka.config;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Slf4j
@Data
public class KafkaConfig   {

    private List<Topic> topics = new ArrayList<>();

    @Autowired
    ApplicationContext applicationContext;


    @Data
    static class Topic {
        private String name;
        private int partitions;
        private int replication;
    }

    /**
     * JSON消息转换器
     */
    @Bean
    public RecordMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    @PostConstruct
    public void init() {
        if (Objects.nonNull(topics)) {
            topics.forEach(a -> {
                ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
                ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
                beanFactory.registerSingleton(a.getName(), new NewTopic(a.getName(), a.getPartitions(), (short) a.getReplication()));
                log.info(a.getName() + "topic注入容器成功");
            });
        }

    }


}
