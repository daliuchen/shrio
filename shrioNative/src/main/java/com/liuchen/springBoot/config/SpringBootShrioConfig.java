package com.liuchen.springBoot.config;

import com.liuchen.springBoot.mapper.MySessionMapper;
import com.liuchen.springBoot.myRealm.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: shrio
 * @description: Springboot和shiro整合的配置类
 * @author: lc
 * @date: 2020/6/27
 **/
@Configuration
public class SpringBootShrioConfig {

    @Value("${redis.port}")
    private int port;
    @Value(("${redis.host}"))
    private String host;



    /**
     * 声明自定义的realm
     */
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    //配置安全管理器 配置安全管理器的时候把 realm配置进去。
    @Bean
    public SecurityManager securityManager(MyRealm realm) {
        //使用默认的安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        //将自定义的realm交给安全管理器统一调度管理  return securityManager;
        securityManager.setRealm(realm);

        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        //1.创建shiro过滤器工厂
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        //2.设置安全管理器
        filterFactory.setSecurityManager(securityManager);
        //3.通用配置(配置登录页面，登录成功页面，验证未成功页面)
        filterFactory.setLoginUrl("/autherror?code=1"); //设置登录页面
        filterFactory.setUnauthorizedUrl("/autherror?code=2"); //授权失败跳转页面
        // 4.配置过滤器集合
        /*
        key :访问连接 支持通配符的形式
        value:过滤器类型 shiro常用过滤器
        anno :匿名访问(表明此链接所有人可以访问)
        authc :认证后访问(表明此链接需登录认证成功之后可以访问)
        */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        /**
         * 拦截器的顺序是按照写的顺序的。
         * 配置权限的时候，可以在拦截的这里，写明 那些请求需要什么权限。
         */
        filterMap.put("/user/login", "anon");
//        filterMap.put("/user/aboutMe","roles[客户经理]");
        filterMap.put("/user/**", "authc");
        //5.设置过滤器
        filterFactory.setFilterChainDefinitionMap(filterMap);
        return filterFactory;
    }
    //配置shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    //配置shiro redisManager
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host); redisManager.setPort(port);
        return redisManager;
    }
    //配置shiro的缓存管理器
    //使用redis实现
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     *  RedisSessionDAO shiro sessionDao层的实现
     *  通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    /* 3.会话管理器 */
    public DefaultWebSessionManager sessionManager() {
        MySessionMapper sessionManager = new MySessionMapper();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

}
