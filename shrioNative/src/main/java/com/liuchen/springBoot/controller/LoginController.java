package com.liuchen.springBoot.controller;

import cn.hutool.json.JSONUtil;
import com.liuchen.NativeTest;
import com.liuchen.shriotest.shrio.entity.ApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @program: shrio
 * @description:
 * @author: lc
 * @date: 2020/6/26
 **/
@RestController
@RequestMapping("/user")
public class LoginController {
    Logger logger = LogManager.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult userLogin(String userName, String password) {
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
            subject.login(usernamePasswordToken);
            return ApiResult.SUCCESS("登录成功");
        } catch (UnknownAccountException uae) {
            //用户名不存在
            logger.info("用户不存在:{}", userName);
            logger.info(uae.getMessage());
        } catch (IncorrectCredentialsException ice) {
            //密码不匹配
            logger.info("密码错误:{}", userName);
            logger.info(ice.getMessage());
        } catch (LockedAccountException lae) {
            //账户或者密码锁定
            logger.info("账号锁定:{}", userName);
            logger.info(lae.getMessage());
        } catch (AuthenticationException ae) {
            logger.info("未通过授权:{}", userName);
            logger.info(ae.getMessage());
        }
        return ApiResult.Fail("登录失败");
    }

    @RequiresRoles(value = "客户经理")
    @RequestMapping(value = "/aboutMe", method = RequestMethod.POST)
    public ApiResult aboutMe() {
        return ApiResult.SUCCESS("about me ");
    }

    @RequiresPermissions(value = "/user/session")
    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public ApiResult saveUser(HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            String name = attributeNames.nextElement();
            Object attribute = session.getAttribute(name);
            logger.info("session中保存的信息:{}",JSONUtil.toJsonStr(attribute));
        }
        return ApiResult.SUCCESS("save user");
    }
    @RequiresPermissions(value = "/user/delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ApiResult deleteUser() {
        return ApiResult.SUCCESS("delete user");
    }
}









