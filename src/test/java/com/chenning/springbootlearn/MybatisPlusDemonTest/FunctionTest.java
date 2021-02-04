package com.chenning.springbootlearn.MybatisPlusDemonTest;

import com.alibaba.fastjson.JSON;
import com.chenning.springbootlearn.SpringBootLearnApplication;
import com.chenning.springbootlearn.demonBuild.model.Card;
import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.demonBuild.service.CardService;
import com.chenning.springbootlearn.demonBuild.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-02-02 15:43
 **/
@SpringBootTest(classes = SpringBootLearnApplication.class)
@RunWith(SpringRunner.class)
public class FunctionTest {
    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;
    @Test
    public void findAllUser() {
        List<User> allUser = userService.findAllUser();
        System.out.println(JSON.toJSONString(allUser));
    }

    @Test
    public void findAllCard() {
        List<Card> allCard = cardService.findAllCard();
        System.out.println(JSON.toJSONString(allCard));
    }
}
