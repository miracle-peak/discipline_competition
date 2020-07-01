package com.gxuwz.subject.common.util;

import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @author: 蔡奇峰
 * @date: 2020/5/26 22:34
 **/
@Slf4j
@Component
public class TokenUtil {

    /** 七牛云密钥*/
    private static final String AK = "hshXCWFhJA87SHY6G0-AVQ16pNEfQFv6ZbkHe7-5";
    private static final String SK = "uqnhNRotTsyjCsvEwOCVc4SZkvehLqn1tee0lWrT";

    /** 七牛云工作空间*/
    public static final String BUCKET = "miraclepeak";

    /**
     * 存七牛云token的redis key
     */
    public static final String KEY = "qiniukey";
    private static final String DEADLINE = "deadline";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisUtil jedisUtil;


    /**
     * 获取七牛云的token（从redis中获取）
     *
     * @return
     */
    public String getRedisToken(){
        Boolean hasKey = redisTemplate.hasKey(KEY);
        if (hasKey){
            String token = jedisUtil.getStr(KEY);

            return token;
        }else {
//            Auth auth = Auth.create(AK, SK);
//            String token = auth.uploadToken(BUCKET);

            String token = getUploadToken();
            // 过期时间一个小时
            redisTemplate.opsForValue().set(KEY, token, 1, TimeUnit.HOURS);

            return token;
        }
    }


    /**
     *  获取七牛云上传token（直接获取）
     *
     * @return
     */
    public static String getUploadToken(){
        Auth auth = Auth.create(AK, SK);

        return auth.uploadToken(BUCKET);
    }

}
