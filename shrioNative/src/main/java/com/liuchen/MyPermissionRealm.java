package com.liuchen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: shrio
 * @description: 自定义Realm
 * @author: lc
 * @date: 2020/6/26
 **/
public class MyPermissionRealm extends AuthorizingRealm {
    Logger logger = LogManager.getLogger(NativeTest.class);

    /**
     * 给这个realm给个名字
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("MyPermissionRealm");
    }

    /**
     * 授权:授权的主要目的就是查询数据库获取用户的所有角色和权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.从principals获取已认证用户的信息
        String username = (String) principalCollection.getPrimaryPrincipal();
        /**
         * 正式系统:应该从数据库中根据用户名或者id查询 * 这里为了方便演示，手动构造
         * 实际中，这里就是显示的是配置的权限，一般都是页面的路由地址和请求的url
         */
        // 2.模拟从数据库中查询的用户所有权限
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:save");
        permissions.add("user:delete");
        permissions.add("user:update");// 商品更新权限


        // 3.模拟从数据库中查询的用户所有角色
        /**
         * 实际中这些个角色可能就是 部门经理啊，人事啊。这些角色.
         */
        List<String> roles = new ArrayList<String>();
        roles.add("role1");
        roles.add("role2");

        // 4.构造权限数据
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 5.将查询的权限数据保存到 simpleAuthorizationInfo
        simpleAuthorizationInfo.addStringPermissions(permissions);
        // 6.将查询的角色数据保存到simpleAuthorizationInfo
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证:认证的主要目的，比较用户输入的用户名密码是否和数据库中的一致
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        /**
         * 实际来说，这里都是从数据库中查找数据的. 这里我就模拟一下就好了
         */
        if ("admin".equals(username) && "admin".equals(password)) {
            logger.info("{} 登录成功:", username);
            return new SimpleAuthenticationInfo(username, password, this.getName());
        } else {
            throw new RuntimeException("用户名或者密码错误");
        }

    }
}