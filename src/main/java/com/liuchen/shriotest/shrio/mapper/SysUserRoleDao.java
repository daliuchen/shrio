package com.liuchen.shriotest.shrio.mapper;

import com.liuchen.shriotest.shrio.entity.SysUserRole;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<Integer> listRoleByUserId(Integer userId);
}