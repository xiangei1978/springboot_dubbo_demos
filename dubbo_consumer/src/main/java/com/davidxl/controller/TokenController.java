package com.davidxl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.davidxl.dubbo.service.SysEmployeeService;
import com.davidxl.model.SysEmployee;
import com.davidxl.service.system.token.EmployeeTokenService;
import com.davidxl.util.VerifyCodeUtil;
import com.davidxl.web.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Api(value="token controller")
@RestController
@Slf4j
public class TokenController {

    @Autowired
    EmployeeTokenService employeeTokenService;

    @Reference(version="1.1.0")
    SysEmployeeService sysEmployeeService;

    @ApiOperation(value="获取token", notes="获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "loginID", value = "用户id", defaultValue = "admin", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", defaultValue = "admin", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "verifyCode", value = "验证码", defaultValue = "1234", dataType = "string"),
    })
    @RequestMapping(value="/getToken", method= RequestMethod.GET)
    public CommonResult getToken(HttpServletRequest httpServletRequest, String loginID, String password, String  verifyCode ){

        CommonResult r = new CommonResult();
//      测试jmeter暂时跳过
//        if (!VerifyCodeUtil.checkVerifyCode(httpServletRequest,verifyCode)){
//            r.setErrMsg("验证码验证错误");
//            return r;
//        }

        //略过验证用户名密码合法性
        //check user
        SysEmployee employee =  sysEmployeeService.selectByLoginIdAndPass(loginID,DigestUtils.sha1Hex(password));

        Assert.notNull(employee,"登陆\"用户名\" 或 \"密码\" 错误！");
       // employeeTokenService.clearEmployeeFromCache(employee.getEmployeeId());
        r.setData(employeeTokenService.createEmployeeNewToken(httpServletRequest, employee));
        return   r  ;
    }
}
