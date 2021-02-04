package com.chenning.springbootlearn.handerJobDemon.service.impl;

import com.chenning.springbootlearn.demonBuild.mapper.UserMapper;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.handerJobDemon.DemonModel;
import com.chenning.springbootlearn.handerJobDemon.service.UserInfoGetter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 16:49
 **/
public class UserInfoGetterImpl implements UserInfoGetter {
    @Autowired
    private UserMapper mapper;
    @Override
    public void initUserInfo(DemonModel demonModel) {
        Integer id=demonModel.getId();
        User user = mapper.selectById(id);
        demonModel.setUser(user);
    }
}
