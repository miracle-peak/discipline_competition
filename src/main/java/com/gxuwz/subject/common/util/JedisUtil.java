package com.gxuwz.subject.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 11:28
 *
 * @Version V1.0
 **/
@Component
public class JedisUtil {
    Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储jwt
     * @param key
     * @param value
     * @param expireTime
     */
    public boolean setToken(String key, Object value, long expireTime) {
        try {
            redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 获取jwt
     * @param key
     * @return
     */
    public String getToken(String key){
        try {
            String reslut = (String)redisTemplate.opsForValue().get(key);

            return reslut;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * get
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
            return false;
        }
    }
}
