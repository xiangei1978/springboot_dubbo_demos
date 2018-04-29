package com.davidxl.service;

import com.davidxl.model.User;

/**
 * Created by xianglei on 2018/4/17.
 */
public interface UserService {

    int insertSelective(User user);

    User selectByPrimaryKey(Integer id);
}
