package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_auth
 * @author 
 */
@Data
public class SysAuth implements Serializable {
    private Integer id;

    /**
     * 权限名
     */
    private String name;

    private String authName;

    /**
     * 0：页面路由，1：权限
     */
    private String type;

    /**
     * 父级路由
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 排序
     */
    private Integer rank;

    private static final long serialVersionUID = 1L;
}