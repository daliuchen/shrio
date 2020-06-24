package com.liuchen.shriotest.shrio.config;

import com.liuchen.shriotest.shrio.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
@Configuration
public class CommonConfig {
    @Bean
    public JwtUtil jwtUtil(){
       return   new JwtUtil();
    }
}
