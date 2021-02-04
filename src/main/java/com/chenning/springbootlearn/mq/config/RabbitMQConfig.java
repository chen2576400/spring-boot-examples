package com.chenning.springbootlearn.mq.config;

import com.chenning.springbootlearn.mq.constant.Constant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Mr.Chen
 * @create: 2021-02-03 10:50
 **/
@Configuration
public class RabbitMQConfig {

/*    Direct Exchange - 处理路由键。需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
    这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，
    不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
    */
 /*   Fanout Exchange 、Topic Exchange详情可以自由查询*/

    @Bean
    public Exchange directExchange() {
        Exchange exchange = new DirectExchange("Direct", true, false);
        return exchange;
    }


    @Bean
    public Queue TaskOne() {
        return new Queue(Constant.QUEUE_ONE, true);
    }

    @Bean
    public Queue TaskTwo() {
        return new Queue(Constant.QUEUE_TWO, true);
    }

    @Bean
    public Queue TaskThree() {
        return new Queue(Constant.QUEUE_THREE, true);
    }



    /**
     * 绑定队列到自定义交换机
     *
     * @return
     */
    @Bean
    public Binding bindingNotifyOfTaskOne() {  //使用Direct交换机
        return BindingBuilder.bind(TaskOne()).to(directExchange()).with(Constant.QUEUE_ONE).noargs();
    }

    @Bean
    public Binding bindingNotifyOfTaskTwo() {  //使用Direct交换机
        return BindingBuilder.bind(TaskTwo()).to(directExchange()).with(Constant.QUEUE_TWO).noargs();
    }

    @Bean
    public Binding bindingNotifyOfTaskThree() {  //使用Direct交换机
        return BindingBuilder.bind(TaskThree()).to(directExchange()).with(Constant.QUEUE_THREE).noargs();
    }
}
