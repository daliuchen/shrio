package com.liuchen.shriotest.shrio.util;

import lombok.SneakyThrows;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
public class TokenStringUtil {
    @SneakyThrows(Exception.class)
    public static String substrToken(String headr){
        if(headr.contains("Bearer")){
            return  headr.substring(6 + 1);
        }
        return null;
    }
}
