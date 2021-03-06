package com.liuchen.springBoot.mapper;

import com.liuchen.shriotest.shrio.entity.SysAuth;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysAuthDao1 {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAuth record);

    int insertSelective(SysAuth record);

    SysAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAuth record);

    int updateByPrimaryKey(SysAuth record);

    List<SysAuth> listAuthByIds(List<Integer> auths);
}