package com.gxuwz.subject.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxuwz.subject.common.constant.StatusCode;
import com.gxuwz.subject.common.util.*;
import com.gxuwz.subject.model.JwtValidate;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 13:28
 * @Version V1.0
 **/
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (! uri.contains("/user/login") && ! uri.contains("/file") && ! uri.contains("/user/add")){ // 不拦截登录请求
            String jwt = request.getHeader("Authorization");

            Map<String, Object> resp = new HashMap<>();

            if (! StringUtils.isEmpty(jwt)){
                // 验证jwt
                JwtValidate validate = JWTUtil.validateJwt(jwt);

                if (! validate.isSuccess()){ // jwt验证不通过
                    resp.put("message", "对不起！您的token 有误！validate result :token error");
                    resp.put("code", validate.getErrCode());

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }

                // 解密jwt
                Claims claims = validate.getClaims();

                if (claims.containsKey("id")){

                    String id = claims.get("id").toString();

                    String token = jedisUtil.getToken(id);

                    if (! jwt.equals(token)){// jwt不一致
                        resp.put("message", "对不起！您的token 有误！token error");
                        resp.put("code", StatusCode.TOKEN_ERROR);

                        ResponseUtil.returnJson(response, resp);

                        return false;
                    }

                }else {// 可能是伪造的jwt
                    resp.put("message", "对不起！您的token 有误！token error");
                    resp.put("code", StatusCode.TOKEN_ERROR);

                    ResponseUtil.returnJson(response, resp);
                    return false;
                }

            }else {// 被拦截时也给出响应
                resp.put("message", "对不起！您木有权限！请尝试登录");
                resp.put("code", StatusCode.TOKEN_NONE);

                ResponseUtil.returnJson(response, resp);

                return false;
            }
        }

        return true;
    }

}
