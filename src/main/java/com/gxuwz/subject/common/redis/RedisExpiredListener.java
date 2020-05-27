package com.gxuwz.subject.common.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * author: 蔡奇峰
 * date: 2020/5/6 14:12
 **/
public class RedisExpiredListener implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 建议使用: valueSerializer
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        //Redis数据的键
        String redisId = new String(body);

        System.out.println("onMessage >> " );
        System.out.println(String.format("channel: %s \n body: %s \n bytes: %s"
                ,new String(channel), new String(body), new String(pattern)));
    }
}
