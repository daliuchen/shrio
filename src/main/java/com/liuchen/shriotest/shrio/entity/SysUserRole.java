package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_user_role
 * @author 
 */
@Data
public class SysUserRole implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}