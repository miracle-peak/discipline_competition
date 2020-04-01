package com.gxuwz.subject.common.util;

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
     * @param day
     */
    public boolean setToken(String key, Object value, int day) {
        int second = day * 60 * 60 * 24;

        try {
            redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);

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
            String result = (String)redisTemplate.opsForValue().get(key);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
