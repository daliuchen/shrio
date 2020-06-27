package com.liuchen.springBoot.controller;

import com.liuchen.shriotest.shrio.entity.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: shrio
 * @description:
 * @author: lc
 * @date: 2020/6/27
 **/
@RestController
public class AuthComtroller {
    @RequestMapping(value = "/autherror", method = RequestMethod.GET)
    public ApiResult auth(Integer code) {
        if (1 == code) {
            return ApiResult.Fail("用户没有登录，请先登录");
        }
        if (2 == code) {
            return ApiResult.Fail("没有权限");
        }
        return ApiResult.SUCCESS();
    }

}
