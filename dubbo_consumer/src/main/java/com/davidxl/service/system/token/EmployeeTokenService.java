package com.davidxl.service.system.token;


import com.davidxl.common.status.TokenStatus;
import com.davidxl.model.SysEmployee;
import com.davidxl.model.sys.EmployeeTokenCache;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface EmployeeTokenService {


    //根据employeeId从redis中获取EmployeeCache
    EmployeeTokenCache getEmployeeFromCache(Integer employeeId)  ;


    //以user：#employee.id 为key，将 user 放入缓存
    EmployeeTokenCache putEmployeeInCache(EmployeeTokenCache employeeCache, Long time);



    EmployeeTokenCache createEmployeeNewToken(HttpServletRequest httpServletRequest, SysEmployee sysEmployee);


    //从redis中删除employee
    void clearEmployeeFromCache(Integer employeeId);



}
