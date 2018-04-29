package com.davidxl.service.impl;

import com.davidxl.dao.UserMapper;
import com.davidxl.model.User;
import com.davidxl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by xianglei on 2018/4/17.
 */
@Service
//@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);

    }

    @Override
   @Cacheable(value="users", key="'user_'+#id")
//    @Cacheable(value="products", key="'user_'+#id")
    public User selectByPrimaryKey(Integer id) {

        return userMapper.selectByPrimaryKey(id);

    }
}
