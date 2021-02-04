package com.chenning.springbootlearn.MybatisPlusDemonTest.mq;

import com.chenning.springbootlearn.SpringBootLearnApplication;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.demonBuild.service.UserService;
import com.chenning.springbootlearn.mq.producer.SenderOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 15:05
 **/
@SpringBootTest(classes = SpringBootLearnApplication.class)
@RunWith(SpringRunner.class)
public class QueueTest {

    @Autowired
    private SenderOne sender;
    @Autowired
    private UserService userService;

    @Test
    public void sendMessage() {
        List<User> allUser = userService.findAllUser();
        sender.sender(allUser);
    }
}
