package com.liuchen.shriotest.shrio.mapper;

import com.liuchen.shriotest.shrio.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser getUserByUserNameAndPassword(String userName,String password);

}