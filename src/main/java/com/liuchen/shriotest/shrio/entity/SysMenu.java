package com.liuchen.shriotest.shrio.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 系统菜单
 * @author: liuchen
 * @date: 2020/6/24
 **/
@Data
public class SysMenu {
    private Integer id;
    private String name;
    private String authUrl;
    private Integer rank;
    private Integer parentId;
    private String type;
    private List<SysMenu> child = new ArrayList<>();

    public SysMenu(SysAuth auth) {
        this.id = auth.getId();
        this.name = auth.getName();
        this.authUrl = auth.getAuthName();
        this.rank = auth.getRank();
        this.parentId = auth.getParentId();
        this.type = "0".equals(auth.getType())?"页面路由":"权限";
    }
}
