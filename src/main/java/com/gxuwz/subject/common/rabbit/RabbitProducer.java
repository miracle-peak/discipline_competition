package com.gxuwz.subject.common.rabbit;

import com.gxuwz.subject.common.constant.RabbitConstant;
import com.gxuwz.subject.model.UserModel;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生产者
 *
 * @author: 蔡奇峰
 * date: 2020/5/5 21:39
 **/
@Component
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     *
     * @param user 注册的用户对象
     */
    public void send(UserModel user){

        CorrelationData correlationData = new CorrelationData(user.getId() + "");

        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE_MAIL,RabbitConstant.ROUTE_MAIL_KEY,
                user, correlationData);
    }

}
