package com.davidxl.dubbo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.davidxl.common.Global;
import com.davidxl.common.ZKLockPrefix;
import com.davidxl.common.type.NormalResultType;
import com.davidxl.dao.LogOperationMapper;
import com.davidxl.dao.UserMapper;
import com.davidxl.event.DemoUserAddEvent;
import com.davidxl.model.LogOperation;
import com.davidxl.model.User;
import com.davidxl.dubbo.service.UserService;
import com.davidxl.util.DateUtil;
import com.davidxl.util.RedisSequenceFactory;
import com.davidxl.util.zk.LockZookeeperClientFactory;
import com.davidxl.util.zk.ZookeeperSharedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.Assert;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by xianglei on 2018/4/17.
 */
@Service(version = "1.1.0")
@Slf4j
@Transactional
//@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserMapper userMapper;

    @Autowired
    LogOperationMapper logOperationMapper;

    @Autowired
    private LockZookeeperClientFactory lockZookeeperClientFactory;


    @Override
    public  int incUserAge(Integer id){

//        User u =   userMapper.selectByPrimaryKey(id);
        User u =   userMapper.selectByPrimaryKeyForUpdate(id);
        if (u !=null) {
            log.debug("1次获得对象：age = " + u.getAge().toString());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            u.setAge(u.getAge() +1 );
            Integer effectNum= userMapper.updateByPrimaryKey(u);

//            User u1 =   userMapper.selectByPrimaryKey(id);
//            log.debug("2次获得对象：effectNum={} age = {}",effectNum,  u1.getAge().toString());

           return effectNum;
        }
        return  0;
//
//        ZookeeperSharedLock zookeeperSharedLock = new ZookeeperSharedLock(lockZookeeperClientFactory,
//                ZKLockPrefix.UserLockPrefix.toString()   );
//        try {
//            boolean result = zookeeperSharedLock.lock(Global.DISTRIBUTED_LOCK_WAIT_TIME, TimeUnit.MILLISECONDS);
//            if (result) {//获取锁成功
//
//               // for (int i = 0; i < 100 ; i++) {
//                    User u =   userMapper.selectByPrimaryKeyForUpdate(id);
//                    if (u !=null) {
////                        try {
////                            Thread.sleep(1000);
////                        } catch (InterruptedException e) {
////                            e.printStackTrace();
////                        }
//
//                        u.setAge(u.getAge() +1 );
//
//                        int effectNum = userMapper.updateByPrimaryKey(u);
//                        Assert.isTrue(effectNum==1,"更新数据库失败");
//
//                        return effectNum;
//                    }
//                //}
//
////                return 1;
//
//            } else{
//                log.error("zk 同步锁未获取成功！");
//            }
//        } catch (Exception e) {
//            log.error("接口异常",e);
//            throw new RuntimeException(e);
//        }finally {
//            try {
//                //释放锁
//                zookeeperSharedLock.release();
//            } catch (Exception e) {
//                throw new RuntimeException();
//            }
//        }
//        log.error("接口异常调用返回0");
//      return 0;
    }

    @Override
    public int insertSelective(User user) {


        LogOperation log = new LogOperation();
        log.setCreateTime(new Date());
        log.setNotes("新建用户:" + JSONObject.toJSONString(user)  );
        log.setTablename("user");
        logOperationMapper.insertSelective(log);
        Assert.state(userMapper.insertSelective(user)==1,"插入数据失败");
        //触发添加用户事件
        applicationContext.publishEvent(new DemoUserAddEvent(this, user));
        return 1;

    }

    @Override
    //使用自己定义的key
//    @Cacheable(value="users", key="'user_'+#id")
    //使用keyGenerator
    @Cacheable(value="users" )
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
