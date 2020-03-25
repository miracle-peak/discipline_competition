package com.gxuwz.subject.util;

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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存储jwt
     * @param key
     * @param value
     * @param time
     */
    public boolean setToken(String key, Object value, int time) {

        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);

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
}
