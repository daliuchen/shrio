package com.liuchen.shriotest.shrio.controller;

import com.liuchen.shriotest.shrio.entity.*;
import com.liuchen.shriotest.shrio.mapper.SysAuthDao;
import com.liuchen.shriotest.shrio.mapper.SysRoleAuthDao;
import com.liuchen.shriotest.shrio.mapper.SysUserDao;
import com.liuchen.shriotest.shrio.mapper.SysUserRoleDao;
import com.liuchen.shriotest.shrio.util.JwtUtil;
import com.liuchen.shriotest.shrio.util.TokenStringUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: liuchen
 * @date: 2020/6/24
 **/
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private SysUserDao userDao;
    @Autowired
    private SysUserRoleDao userRoleDao;
    @Autowired
    private SysRoleAuthDao roleAuthDao;
    @Autowired
    private SysAuthDao authDao;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public ApiResult userLogin(@RequestBody Map maps, @RequestHeader("Authorization") String header){

        String username = (String)maps.get("username");
        String password = (String)maps.get("password");
        System.out.println(username);
        System.out.println(password);
        SysUser loginUser = userDao.getUserByUserNameAndPassword(username, password);
        if(loginUser == null){
            return  ApiResult.Fail(ResultCode.USER_NOT_EXISTS);
        }else{
            System.out.println(TokenStringUtil.substrToken(header));
            /**
             *  查用户的能访问的权限
             *  结果通过jwt放在 token里面。
             */
            List<Integer> roles = userRoleDao.listRoleByUserId(loginUser.getId());
            //查角色的权限

            List<Integer> auths = roleAuthDao.listUserAuthByRoleId(roles);
            List<SysAuth> sysAuths = authDao.listAuthByIds(auths);
            List<SysMenu> sysMenus = handleSysMenu(sysAuths);


            String result = Strings.EMPTY;
            /**
             * jwt加密
             */
            HashMap<String, Object> map = new HashMap<>();
            map.put("auth",sysMenus);
             result = jwtUtil.createToken(String.valueOf(loginUser.getId()), loginUser.getName(), map);
            return ApiResult.SUCCESS(result);
        }
    }


    private List<SysMenu> handleSysMenu(List<SysAuth> sysAuths){
        ArrayList<SysMenu> rootMenu = new ArrayList<>();
        ArrayList<SysMenu> auths = new ArrayList<>();
        ArrayList<SysMenu> childMenu = new ArrayList<>();
        for (SysAuth sysAuth : sysAuths) {
//            父级路由  顶级路由的 0
            Integer parentId = sysAuth.getParentId();
            String type = sysAuth.getType();
            if(0 == parentId){
                rootMenu.add(new SysMenu(sysAuth));
            }else{
                if("1".equals(type)){
                    // '0：页面路由，1：权限
                    auths.add(new SysMenu(sysAuth));
                }else{
                    childMenu.add(new SysMenu(sysAuth));
                }

            }
        }
        rootMenu.sort((t1,t2) -> t2.getRank()-t1.getRank());
        //遍历剩下的节点通过parentId找
        for(int i = 0;i<rootMenu.size();i++){
            SysMenu root = rootMenu.get(i);
            Integer id = root.getId();

            for (int j=0;j<childMenu.size();j++){
                SysMenu child = childMenu.get(j);
                if(id.equals(child.getParentId())){
                    //设置child
                    rootMenu.get(i).getChild().add(child);
                    //给孩子设置auth
                    for (int k=0;k<auths.size();k++){
                        SysMenu auth = auths.get(k);
                        if(child.getId().equals(auth.getParentId())){
                            childMenu.get(j).getChild().add(auth);
                        }
                    }

                    auths.sort((t1,t2) -> t2.getRank()-t1.getRank());
                }
            }
            childMenu.sort((t1,t2) -> t2.getRank()-t1.getRank());
            //给root设置
            for (int e=0;e<auths.size();e++){
                SysMenu auth = auths.get(e);
                if(root.getId().equals(auth.getParentId())){
                    rootMenu.get(i).getChild().add(auth);
                }
            }
        }
      return rootMenu;
    }

}
