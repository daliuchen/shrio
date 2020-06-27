package com.liuchen.springBoot.myRealm;

import com.liuchen.shriotest.shrio.entity.SysAuth;
import com.liuchen.shriotest.shrio.entity.SysRole;
import com.liuchen.shriotest.shrio.entity.SysUser;
import com.liuchen.springBoot.mapper.*;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: shrio
 * @description: 自定义realm
 * @author: lc
 * @date: 2020/6/27
 **/

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao1 userDao;

    @Autowired
    private SysUserRoleDao1 userRoleDao;
    @Autowired
    private SysRoleAuthDao1 roleAuthDao;
    @Autowired
    private SysAuthDao1 authDao;

    @Autowired
    private SysRoleDao1 roleDao;

    @Override
    public void setName(String name) {
        super.setName("myRealm");
    }

    /**
     * 构造授权方法
     * 主要是干两件事情。
     *  - 查找user的角色
     *  - 查找权限。
     *  - 将上面查找的两个 角色 shiro
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser loginUser = (SysUser)principalCollection.getPrimaryPrincipal();
        //查找 用户角色关联表中找 角色的id
        List<Integer> roles = userRoleDao.listRoleByUserId(loginUser.getId());
        //查 角色权限关联表中找  权限的id
        List<Integer> auths = roleAuthDao.listUserAuthByRoleId(roles);
        // 通过权限的id在权限的表中找权限.
        List<SysAuth> sysAuths = authDao.listAuthByIds(auths);
        // 通过角色id在角色的表中找角色
        List<SysRole> sysRoles = roleDao.listRoleById(roles);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //遍历角色，将角色的名字变为一个string数组。因为 SimpleAuthorizationInfo对象要求是这样的，shiro也不知道你的角色是长什么样子的
        List<String> roleString = sysRoles.stream().map(SysRole::getName).collect(Collectors.toList());
        //遍历权限，将权限的名字变为一个string数组。因为 SimpleAuthorizationInfo对象要求是这样的，shiro也不知道你的权限是长什么样子的
        List<String> authString = sysAuths.stream().map(SysAuth::getAuthName).collect(Collectors.toList());
        //添加从数据查找的角色 给shiro
        info.addRoles(roleString);
        //添加从数据库查找的权限 给shiro
        info.addStringPermissions(authString);
        return  info;
    }


    /**
     * 认证方法  通过用户名在数据库里面查找
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = new String(token.getPassword());
        String username = token.getUsername();
        SysUser user = userDao.getUserByUserNameAndPassword(username, password);
        if(user == null){
            throw new AuthenticationException();
        }
        /**
         * 认证通过之后，构建SimpleAuthenticationInfo 对象
         *  第一个参数：要在里面存放的数据 在这里也叫做， principal
         *  第二个参数：这里叫令牌，credentials
         *  第三个参数：realmName，在这里重写了setName的方法，
         */
         return new SimpleAuthenticationInfo(user, password, this.getName());
    }
}
