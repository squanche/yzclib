package com.yangzhichao.yzclib;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.yangzhichao.yzclib.kafka.config.KafkaConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@MapperScan("com.yangzhichao.yzclib.**.dao*")
//@EnableConfigurationProperties(KafkaConfig.class)
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)

public class YzclibApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzclibApplication.class, args);
    }

}
