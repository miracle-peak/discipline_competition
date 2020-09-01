package com.gxuwz.subject.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.common.util.*;
import com.gxuwz.subject.model.JwtValidate;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt拦截验证
 *
 * @author: 蔡奇峰
 * @date: 2020/3/25 13:28
 * @Version V1.0
 **/
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * 登录url
     */
    private static final String LOGIN_URL = "/user/login";
    /**
     * 访问静态文件url
     */
    private static final String FILE_URL = "/file";
    /**
     * 注册url
     */
    private static final String REGISTER_URL = "/user/add";
    /**
     * 健康监控url
     */
    private static final String ACTUATOR_URL = "/actuator";
    /**
     * jwt解密后claims的唯一标识
     */
    private static final String JWT_ID = "id";

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();

        // 不拦截登录,注册
        if (!uri.contains(LOGIN_URL) && !uri.contains(FILE_URL) && !uri.contains(REGISTER_URL)
                && !uri.contains(ACTUATOR_URL)) {
            String jwt = request.getHeader("Authorization");

            Map<String, Object> resp = new HashMap<>(6);

            if (!StringUtils.isEmpty(jwt)) {
                // 验证jwt
                JwtValidate validate = JwtUtil.validateJwt(jwt);

                // jwt验证不通过
                if (!validate.isSuccess()) {
                    resp.put("message", "对不起！您的token 有误！validate result :token error");
                    resp.put("code", validate.getErrCode());

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }

                // 解密jwt
                Claims claims = validate.getClaims();

                if (claims.containsKey(JWT_ID)) {
                    String id = claims.get(JWT_ID).toString();
                    // 获取redis中jwt
                    String token = redisUtil.getToken(id);
                    // jwt不一致
                    if (!jwt.equals(token)) {
                        resp.put("message", "对不起！您的token 有误！token error");
                        resp.put("code", StatusCode.TOKEN_ERROR);

                        ResponseUtil.returnJson(response, resp);

                        return false;
                    }
                    // 没有id, 可能是伪造的jwt
                } else {
                    log.error("可能存在伪造token，无 id key");
                    resp.put("message", "对不起！您的token 有误！token error");
                    resp.put("code", StatusCode.TOKEN_ERROR);

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }
                // 被拦截时也给出响应
            } else {
                resp.put("message", "对不起！您木有权限！请尝试登录");
                resp.put("code", StatusCode.TOKEN_NONE);

                ResponseUtil.returnJson(response, resp);

                return false;
            }
        }

        return true;
    }

}
