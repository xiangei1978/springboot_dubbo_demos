package com.davidxl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.davidxl.common.CheckAuthority;
import com.davidxl.common.status.SexType;
import com.davidxl.dubbo.service.UserService;
import com.davidxl.model.User;
import com.davidxl.util.DateUtil;
import com.davidxl.util.RedisSequenceFactory;
import com.davidxl.web.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
@Api(value="用户模块")
@RestController
@Slf4j
public class TestController {

    @Autowired
    RedisSequenceFactory redisSequenceFactory;

    @Reference(version = "1.1.0")
    private UserService userService;


    @ApiOperation(value="并发测试", notes="并发测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "用户id", defaultValue = "1", dataType = "int"),
    })
    @RequestMapping(value="/test1", method= RequestMethod.GET)
    @CheckAuthority(funcs = {"sys.test.pass","fjadkfaldaf.dld"})
    public CommonResult test(Integer id   ){
        User user = new User();

        long v = redisSequenceFactory.generate("order",DateUtil.getTodayEndTime());
        user.setAge(4);
        user.setName("user" + v);
        user.setAmount(new BigDecimal("12.1"));
        user.setSex(SexType.female);

        Assert.state(userService.insertSelective(user)==1,"insert error");

        CommonResult r = new CommonResult();

        r.setData(1);
        return   r  ;
    }
}
