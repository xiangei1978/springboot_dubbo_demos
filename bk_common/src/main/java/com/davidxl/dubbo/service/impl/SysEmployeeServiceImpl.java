package com.davidxl.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.davidxl.dao.SysEmployeeMapper;
import com.davidxl.dubbo.service.SysEmployeeService;
import com.davidxl.model.SysEmployee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


@Service(version = "1.1.0")
@Slf4j
@Transactional
public class SysEmployeeServiceImpl implements SysEmployeeService {
    @Autowired
    SysEmployeeMapper sysEmployeeMapper;


    public int insertSelective(SysEmployee sysEmployee){

        return  sysEmployeeMapper.insertSelective(sysEmployee);
    }

    public SysEmployee selectByPrimaryKey(Integer employeeId){
        return sysEmployeeMapper.selectByPrimaryKey( employeeId);
    }

    public  SysEmployee selectByLoginIdAndPass(String loginID,String password){
        return  sysEmployeeMapper.selectByLoginIdAndPassword(loginID,password);

    }
}
