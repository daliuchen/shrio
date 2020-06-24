package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_role
 * @author 
 */
@Data
public class SysRole implements Serializable {
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}