package com.liuchen.shriotest.shrio.myIntercept;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
public class AuthIntercept extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * - 得到token 解析
         * - 得到这次请求的request中的url
         * - 看token里面有没有，这里就比较灵活了，在登录之后，可以将这些的登录信息，放在redis中，在这里就可以通过redis操作了。
         * 注意：
         *   这里的权限配置重点在于
         *      token里面的权限是从数据库查的
         *      但是怎么获得url呢
         *         两种方法：
         *          - 传统的request获得servletPath 获得url请求看里面有没有。
         *          - 通过requestMapping的name属性。将 object 的handle变为 HandleMethod对象 获得 RequestMapping 的 annotaion对象获得name属性，然后判断看有没有
         *
         */
        /**
         * 方式一
         */
        String requestURI = request.getRequestURI();
        //下面就看token里面有没有了

        /**
         * 方式二
         */
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestMapping methodAnnotation = handlerMethod.getMethodAnnotation(RequestMapping.class);//这样就有一个弊端 就会限制后端 ，只能写RequestMapping 注解，别的就不能写了。如果是post的话，就只能在type属性里面写了
        String name = methodAnnotation.name();
        //看 auth的name包含不包含这个，只需要在auth表中需要添加一个 独一无二的每一个url的请求。


        //如果有就放行。
        //没有就报错，全局处理异常。ok
    }
}
