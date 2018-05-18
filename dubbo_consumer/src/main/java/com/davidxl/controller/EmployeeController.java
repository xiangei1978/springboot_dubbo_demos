package com.davidxl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.davidxl.dubbo.service.SysEmployeeService;
import com.davidxl.model.SysEmployee;
import com.davidxl.web.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value="Employee 模块")
@RestController
@Slf4j
@RequestMapping(value = {"/employee"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmployeeController {

    @Reference(version = "1.1.0")
    SysEmployeeService sysEmployeeService;

    @ApiOperation(value="添加employee", notes="添加employee")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", value = "令牌", defaultValue = "39MEHmCu4BmumCRSAriORS4lUXcQYSU9cM4DRUJCLL6YWXAvS4igTbSWEQbpvC8U_NlBH8NnwWzfdNtwDDwdzXXQE7wbnt7it31ThNqHnJo", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "body",  name = "sysEmployee", value = "employee 实体",required = true,  dataType = "SysEmployee")
    })
    @RequestMapping(value="/insert", method= RequestMethod.POST)
    public CommonResult insert(HttpServletRequest httpServletRequest,@RequestBody SysEmployee  sysEmployee ) {
        CommonResult r = new CommonResult();
        sysEmployee.setPassword(DigestUtils.sha1Hex(sysEmployee.getPassword()));

        Assert.state(sysEmployeeService.insertSelective(sysEmployee)==1,"数据库操作异常");

        r.setData(1);
        return r;
    }

}
