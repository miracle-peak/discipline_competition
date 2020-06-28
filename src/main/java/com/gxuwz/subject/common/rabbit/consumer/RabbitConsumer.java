package com.gxuwz.subject.common.rabbit.consumer;

import com.gxuwz.subject.common.constant.RabbitConstant;
import com.gxuwz.subject.common.util.MailUtil;
import com.gxuwz.subject.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 消费者
 *
 * @author: 蔡奇峰
 * date: 2020/5/5 21:44
 **/
@Slf4j
@Component
public class RabbitConsumer {

    @Autowired
    private MailUtil mailUtil;

    /**
     * 监听mail队列
     *
     * @param userModel
     */
    @RabbitListener(queues = RabbitConstant.QUEUE_MAIL)
    public void receiver(@Payload UserModel userModel){
        log.info("开始发送邮件.....");

        mailUtil.sendMail(userModel);
    }


}
