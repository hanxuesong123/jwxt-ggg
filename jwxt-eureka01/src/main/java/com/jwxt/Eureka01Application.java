package com.jwxt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //如果一个工程加入EnableEurekaServer注解, 就表明这个项目是eureka的注册中心
public class Eureka01Application {

    public static void main(String[] args) {
        SpringApplication.run(Eureka01Application.class,args);
    }
}
