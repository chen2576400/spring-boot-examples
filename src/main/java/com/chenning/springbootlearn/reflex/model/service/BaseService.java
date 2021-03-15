package com.chenning.springbootlearn.reflex.model.service;

import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.demonBuild.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Mr.Chen
 * @create: 2021-02-04 16:37
 **/
@Service
public class BaseService {
    @Autowired
    UserService userService;

    public void x(User user) {
        System.out.println(user.getUserId() + user.getUserName());
    }


}
