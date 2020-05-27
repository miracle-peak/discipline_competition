package com.gxuwz.subject.common.config;

import com.gxuwz.subject.common.constant.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 *
 * author: 蔡奇峰
 * date: 2020/5/5 21:29
 **/
@Slf4j
@Configuration
public class RabbitConfig {


    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 配置确认机制、回调
     *
     * 将当前配置的RabbitTemplate对象注入ioc容器
     * 可供全局使用即使用RabbitTemplate对象都会有确认机制和回调（spring默认使用单例模式）
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");
            } else {

                log.error("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        });

        return rabbitTemplate;
    }

    /**
     * 序列化
     * @return
     */
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue mail(){

        return new Queue(RabbitConstant.QUEUE_MAIL, true);
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
