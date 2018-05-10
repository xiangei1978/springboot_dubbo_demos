package com.davidxl.dubbo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.davidxl.dao.LogOperationMapper;
import com.davidxl.dao.UserMapper;
import com.davidxl.model.LogOperation;
import com.davidxl.model.User;
import com.davidxl.dubbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by xianglei on 2018/4/17.
 */
@Service(version = "1.1.0")
@Transactional
//@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    LogOperationMapper logOperationMapper;

    @Override
    public int insertSelective(User user) {
        LogOperation log = new LogOperation();
        log.setCreateTime(new Date());
        log.setNotes("新建用户:" + JSONObject.toJSONString(user)  );
        log.setTablename("user");
        logOperationMapper.insertSelective(log);

        return userMapper.insertSelective(user);

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
