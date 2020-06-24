package com.liuchen.shriotest.shrio;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.liuchen.shriotest.shrio.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ShrioApplicationTests {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void contextLoads() {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("name","张狗蛋");
        String token = jwtUtil.createToken("1", "2", stringObjectMap);
        System.out.println(token);
    }

    @Test
    void parentToken(){

        String token  =   "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiMTIzMTIzIiwiaWF0IjoxNTkyOTgyNzQyLCJhdXRoIjpbeyJpZCI6MSwibmFtZSI6IuS4muWKoeWkhOeQhuW5s-WPsCIsImF1dGhVcmwiOiIveW1seS8xIiwicmFuayI6MSwicGFyZW50SWQiOjAsImNoaWxkIjpbeyJpZCI6MiwibmFtZSI6IuS4muWKoemFjee9riIsImF1dGhVcmwiOiIveXdwei8xIiwicmFuayI6MSwicGFyZW50SWQiOjEsImNoaWxkIjpbeyJpZCI6MywibmFtZSI6IuS4muWKoemFjee9rueuoeeQhiIsImF1dGhVcmwiOiIvcXgveXdwei8xIiwicmFuayI6MiwicGFyZW50SWQiOjIsImNoaWxkIjpbXX1dfSx7ImlkIjo0LCJuYW1lIjoi56ys5LiJ5pa557uT5p6E566h55CGIiwiYXV0aFVybCI6Ii95bWx5LzIiLCJyYW5rIjozLCJwYXJlbnRJZCI6MSwiY2hpbGQiOltdfV19XX0.OM8GzZvcwUD0gVK4oYtFoJwsuFO58upfcbHWbFb8aXM";

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiMiIsImlhdCI6MTU5Mjk4MjE1MiwibmFtZSI6IuW8oOeLl-ibiyJ9.ulkihlfDHIu7SA8Zlj0IctbuVKANqAs2hxtgGSraIvk";
        Claims claims = jwtUtil.parseToken(token);
        String s = JSONUtil.toJsonStr(claims);
        System.out.println(s);
    }

}
