package com.liuchen.shriotest.shrio;

import com.liuchen.shriotest.shrio.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liuchen.shriotest.shrio.mapper")
public class ShrioApplication {

    @Autowired
    private JwtUtil jwtUtil;
    public static void main(String[] args) {
        SpringApplication.run(ShrioApplication.class, args);
    }


}
