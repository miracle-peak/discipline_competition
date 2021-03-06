package com.gxuwz.subject.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具
 *
 * @author: 蔡奇峰
 * @date: 2020/3/25 11:28
 * @Version V1.0
 **/
@Component
public class RedisUtil {
    Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 存储jwt
     *
     * @param key
     * @param value
     * @param expireTime 过期时间 单位秒
     */
    public boolean setToken(String key, Object value, long expireTime) {
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.MILLISECONDS);

        }catch (Exception e){
            logger.error("存储token失败:{}", e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 获取jwt
     *
     * @param key
     * @return
     */
    public String getToken(String key){
        try {
            String result = (String)redisTemplate.opsForValue().get(key);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取String值通用方法
     *
     * @param key
     * @return
     */
    public String getStr(String key){
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value != null && ! "".equals(value)){
                return (String) value;
            }
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }

    /**
     *
     * @param key
     * @param value
     * @param expireTime 过期时间单位秒
     * @return
     */
    public boolean setStr(String key, String value, int expireTime){
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);

            return true;
        }catch (Exception e){
            logger.error("设置key失败:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public boolean deleteStr(String key){
        boolean delete = redisTemplate.opsForValue().getOperations().delete(key);

        return delete;
    }
}
