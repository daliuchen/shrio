package com.liuchen.shriotest.shrio.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUser implements Serializable {
    private Integer id;

    private String name;

    private String password;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}