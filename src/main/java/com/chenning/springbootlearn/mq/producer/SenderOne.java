package com.chenning.springbootlearn.mq.producer;

import com.chenning.springbootlearn.demonBuild.model.User;
import com.chenning.springbootlearn.mq.constant.Constant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 14:57
 **/
@Service
public class SenderOne {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sender(List<User> list) {
        this.rabbitTemplate.convertAndSend("Direct", Constant.QUEUE_ONE, list);
    }
}
