package com.liuchen;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @program: shrio
 * @description: 原生test
 * @author: lc
 * @date: 2020/6/26
 **/
public class NativeTest {
    /**
     *  这里的值就是在 shiro.ini 配置的值
     */
    public static String userName = "admin";
    /**
     *  这里的值就是在 shiro.ini 配置的值
     */
    public static String password = "admin";

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(NativeTest.class);

        //1.加载ini配置文件创建SecurityManager工厂类
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.获取securityManager
        SecurityManager securityManager = factory.getInstance();
        //3.将securityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        //4.创建主体(此时的主体还为经过认证)
        Subject currentUser = SecurityUtils.getSubject();
        /**
         * 这里啊 就假设数据是从前端传过来的 用户名和密码了
         */
        //判断当前用户是否登录过。也就是是否通过了 Authentication 认证了。
        if (!currentUser.isAuthenticated()) {
            try {
                logger.info("用户开始登录:{}", userName);
                //5.构造主体登录的凭证(即用户名/密码)
                //第一个参数:登录用户名，第二个参数:登录密码
                UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
                //6.主体登录
                currentUser.login(token);
                //7.验证是否登录成功
                logger.info("用户登录成功= {}", currentUser.isAuthenticated());
//                System.out.println("用户登录成功=" + currentUser.isAuthenticated());
                //getPrincipal 获取登录成功的安全数据
                System.out.println(currentUser.getPrincipal());

                //7.用户认证成功之后才可以完成授权工作
               logger.info("{}是否有user:save的权限:{}",userName,currentUser.isPermitted("user:save"));
                logger.info("{}是否有role1的权限:{}",userName,currentUser.hasRole("role1"));

                //登录尝试失败，可以捕获各种特定的异常，这些异常可以准确地告诉发生了什么，并允许进行相应的处理和响应：
                //在这里 可以根据业务需求来匹配。详情请看 http://shiro.apache.org/static/current/apidocs/org/apache/shiro/authc/AuthenticationException.html
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
        }
    }
}
