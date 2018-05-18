package com.davidxl.dao;

import com.davidxl.model.SysPermissions;

public interface SysPermissionsMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(SysPermissions record);

    int insertSelective(SysPermissions record);

    SysPermissions selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(SysPermissions record);

    int updateByPrimaryKey(SysPermissions record);
}