package com.liuchen.shriotest.shrio.entity;

public enum ResultCode {
    USER_NOT_EXISTS(404,"用户不存在");


    private int code;
    private String name;



    ResultCode(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
