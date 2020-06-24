package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_role_auth
 * @author 
 */
@Data
public class SysRoleAuth implements Serializable {
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer authId;

    private static final long serialVersionUID = 1L;
}