package com.liuchen.springBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: shrio
 * @description: 主启动类
 * @author: lc
 * @date: 2020/6/26
 **/
@SpringBootApplication
@MapperScan("com.liuchen.springBoot.mapper")
public class MainShiroApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainShiroApplication.class,args);
    }
}
