package com.liuchen.shriotest.shrio.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
@Data
public class ApiResult {

    private Integer code;
    private String msg;
    private Object data;




    public ApiResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiResult SUCCESS(Object object){
        return new ApiResult(200,null,object);
    }
    public static ApiResult SUCCESS(){
        return new ApiResult(200,null,null);
    }
    public static ApiResult Fail(Object object){
        return new ApiResult(500,null,object);
    }
}
