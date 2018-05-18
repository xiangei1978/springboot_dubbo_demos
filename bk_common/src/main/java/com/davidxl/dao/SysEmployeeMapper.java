package com.davidxl.dao;

import com.davidxl.model.SysEmployee;
import org.apache.ibatis.annotations.Param;

public interface SysEmployeeMapper {
    int deleteByPrimaryKey(Integer employeeId);

    int insert(SysEmployee record);

    int insertSelective(SysEmployee record);

    SysEmployee selectByPrimaryKey(Integer employeeId);

    SysEmployee selectByLoginIdAndPassword(@Param("loginId") String loginId,@Param("password") String password);

    int updateByPrimaryKeySelective(SysEmployee record);

    int updateByPrimaryKey(SysEmployee record);
}