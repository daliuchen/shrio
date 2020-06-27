package com.liuchen.springBoot.mapper;

import com.liuchen.shriotest.shrio.entity.SysRole;
import com.liuchen.shriotest.shrio.entity.SysRoleAuth;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleAuthDao1 {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAuth record);

    int insertSelective(SysRoleAuth record);

    SysRoleAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAuth record);

    int updateByPrimaryKey(SysRoleAuth record);

    List<Integer> listUserAuthByRoleId(List<Integer> roles);


}