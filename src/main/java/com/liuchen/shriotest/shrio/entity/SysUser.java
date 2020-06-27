package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements Serializable, AuthCachePrincipal {
    private Integer id;

    private String name;

    private String password;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}