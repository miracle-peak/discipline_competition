package com.gxuwz.subject.common.redis;

import com.gxuwz.subject.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.util.StringUtils;

/**
 * 监听过期的 redis key 并删除
 *
 * @author: 蔡奇峰
 * date: 2020/5/6 13:51
 **/
@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 客户端监听订阅的topic，当有消息的时候，会触发该方法;
     * 并不能得到value, 只能得到key。
     * 姑且理解为: redis服务在key失效时(或失效后)通知到java服务某个key失效了, 那么在java中不可能得到这个redis-key对应的redis-value。
     *      * 解决方案:
     *  创建copy/shadow key, 例如 set vkey "vergilyn"; 对应copykey: set copykey:vkey "" ex 10;
     *  真正的key是"vkey"(业务中使用), 失效触发key是"copykey:vkey"(其value为空字符为了减少内存空间消耗)。
     *  当"copykey:vkey"触发失效时, 从"vkey"得到失效时的值, 并在逻辑处理完后"del vkey"
     *
     * 缺陷:
     *  1: 存在多余的key; (copykey/shadowkey)
     *  2: 不严谨, 假设copykey在 12:00:00失效, 通知在12:10:00收到, 这间隔的10min内程序修改了key, 得到的并不是 失效时的value.
     *  (第1点影响不大; 第2点貌似redis本身的Pub/Sub就不是严谨的, 失效后还存在value的修改, 应该在设计/逻辑上杜绝)
     *  当"copykey:vkey"触发失效时, 从"vkey"得到失效时的值, 并在逻辑处理完后"del vkey"
     *
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        String expireKey = message.toString();

        log.warn("redis key 过期：{}", expireKey);

        if (! StringUtils.isEmpty(expireKey)){
            redisUtil.deleteStr(expireKey);
            log.info("删除过期的redis key：{}", expireKey);
        }

    }
}
