package com.gxuwz.subject.common.config;

import com.gxuwz.subject.common.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * author: 蔡奇峰
 * date: 2020/5/5 21:29
 **/
@Configuration
public class RabbitConfig {


    @Bean
    public Queue mail(){

        return new Queue(RabbitConstant.QUEUE_MAIL);
    }

    @Bean
    public TopicExchange exchange(){

        return new TopicExchange(RabbitConstant.EXCHANGE_MAIL);
    }

    @Bean
    public Binding mailBinding(){

        return BindingBuilder.bind(mail()).to(exchange()).with(RabbitConstant.ROUTE_MAIL_KEY);
    }


}
