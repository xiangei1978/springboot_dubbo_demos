package com.davidxl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.davidxl.common.CheckAuthority;
import com.davidxl.common.Global;
import com.davidxl.common.ZKLockPrefix;
import com.davidxl.common.status.SexType;
import com.davidxl.dubbo.service.UserService;
import com.davidxl.model.User;
import com.davidxl.util.DateUtil;
import com.davidxl.util.RedisSequenceFactory;
import com.davidxl.util.zk.LockZookeeperClientFactory;
import com.davidxl.util.zk.ZookeeperSharedLock;
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
import java.util.concurrent.TimeUnit;

@Api(value="用户模块")
@RestController
@Slf4j
public class TestController {

    @Autowired
    RedisSequenceFactory redisSequenceFactory;

    @Reference(version = "1.1.0")
    private UserService userService;

    @Autowired
    private LockZookeeperClientFactory lockZookeeperClientFactory;


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


    @ApiOperation(value="动态日志测试1", notes="动态日志测试1")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "str",
                    value = "str", required = true, dataType = "string")
    })
    @RequestMapping(value="/autoLog1", method= RequestMethod.POST)
    public CommonResult autoLog1(String str){
        CommonResult r = new CommonResult();

        ZookeeperSharedLock zookeeperSharedLock = new ZookeeperSharedLock(lockZookeeperClientFactory,
                ZKLockPrefix.UserLockPrefix.toString()   );
        try {
            boolean result = zookeeperSharedLock.lock(Global.DISTRIBUTED_LOCK_WAIT_TIME, TimeUnit.MILLISECONDS);

            if (result) {//获取锁成功

                log.debug("动态日志测试： autoLog"+str);
                log.info("动态日志测试： autoLog"+str);
                log.error("动态日志测试： autoLog"+str);

            }else {

                throw new RuntimeException("获取锁异常！！");
            }
        } catch (Exception e) {
            log.error("接口异常",e);
            throw new RuntimeException(e);
        }finally {
            try {
                //释放锁
                zookeeperSharedLock.release();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }




        return   r ;

    }

}
