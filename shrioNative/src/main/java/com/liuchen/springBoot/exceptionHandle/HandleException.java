package com.liuchen.springBoot.exceptionHandle;

import com.liuchen.shriotest.shrio.entity.ApiResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: shrio
 * @description:
 * @author: lc
 * @date: 2020/6/27
 **/
@RestController
@ControllerAdvice
public class HandleException {
    @ExceptionHandler(AuthorizationException.class)
    public ApiResult noAuth() {
        return ApiResult.Fail("没有权限");
    }
}
