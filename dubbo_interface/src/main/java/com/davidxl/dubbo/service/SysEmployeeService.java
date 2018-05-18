package com.davidxl.dubbo.service;

import com.davidxl.model.SysEmployee;
import com.davidxl.model.User;

/**
 * Created by xianglei on 2018/4/17.
 */
public interface SysEmployeeService {

    int insertSelective(SysEmployee sysEmployee);

    SysEmployee selectByPrimaryKey(Integer employeeId);

    SysEmployee selectByLoginIdAndPass(String loginID,String password);
}
