package com.gxuwz.subject.common.util;

import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.model.JwtValidate;
import com.gxuwz.subject.model.UserModel;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private RedisUtil redisUtil;


    /**
     * 获取七牛云的token（从redis中获取）
     *
     * @return
     */
    public String getRedisToken(){
        Boolean hasKey = redisTemplate.hasKey(KEY);
        if (hasKey){
            String token = redisUtil.getStr(KEY);

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

    /**
     * 验证jwt,首次登录则创建,并把存到redis
     *
     * @param one 登录的用户
     * @param token 需要验证的jwt
     * @param expireTime 过期时间
     * @return
     */
    public boolean valid(UserModel one, String token, Date expireTime){
        boolean flag = true;
        // 获取过期时间的时间戳
        long time = expireTime.getTime();
        // 不存在这个token
        if (StringUtils.isEmpty(token)) {
            // 创建jwt
            token = JwtUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);
            // 存jwt到redis过期时间6天
            flag = redisUtil.setToken(one.getId() + "", token, time);
        }else{// 存在jwt（token）
            // 验证jwt
            JwtValidate validate = JwtUtil.validateJwt(token);

            // 验证不通过
            if (! validate.isSuccess()){
                // jwt过期
                if (validate.getErrCode() == StatusCode.JWT_EXPIRE){
                    redisUtil.deleteStr(one.getId() + "");
                    // 创建jwt
                    token = JwtUtil.createToken(one.getId() + "", one.getUserName(), one.getUtype(), expireTime);
                    // 存jwt到redis过期时间6天
                    flag = redisUtil.setToken(one.getId() + "", token, time);
                }
                // TODO 其他错误未处理
            }

        }

        return flag;
    }

}
