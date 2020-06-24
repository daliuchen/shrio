package com.liuchen.shriotest.shrio.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
@EnableConfigurationProperties(JwtUtil.class)
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    /**
     * key
     */
    private String key;
    /**
     *  过期时间
     */
    private Long ttl = 10000L;

    public  String createToken(String id,String subject,Map<String,Object> maps){
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, "123123123123123123123123") .setIssuedAt(new Date());
        //设置域名
        for (Map.Entry<String,Object> entry:maps.entrySet()){
            jwtBuilder.claim(entry.getKey(),entry.getValue());
        }

        return jwtBuilder.compact();
    }
    //解析
    public Claims parseToken(String token){
        Claims body = Jwts.parser().setSigningKey("123123123123123123123123")
                .parseClaimsJws(token).getBody();
        return body;
    }

}
