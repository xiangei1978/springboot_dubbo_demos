package com.davidxl.dubbo.service;

import com.davidxl.model.User;

/**
 * Created by xianglei on 2018/4/17.
 */
public interface UserService {

    int insertSelective(User user);

    int incUserAge(Integer id);

    User selectByPrimaryKey(Integer id);
}
