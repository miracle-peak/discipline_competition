package com.gxuwz.subject.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gxuwz.subject.util.JWTUtil;
import com.gxuwz.subject.util.JedisUtil;
import com.gxuwz.subject.util.ResponseUtil;
import com.gxuwz.subject.util.ResultCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
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

        StringBuffer url = request.getRequestURL();
        System.out.println("url--->" + url);

        System.out.println("interceptor--->" + uri);

        if (! uri.contains("/user/login")){// 不拦截登录请求
            String jwt = request.getHeader("Authorization");
            Map<String, Object> resp = new HashMap<>();

            if (!StringUtils.isEmpty(jwt)){
                // 解密jwt
                Claims claims = JWTUtil.parseJwt(jwt);

                if (claims.containsKey("id")){

                    String id = claims.get("id").toString();
                    System.out.println("id-->" + id);

                    String token = jedisUtil.getToken(id);
                    if (! jwt.equals(token)){// jwt不一致
                        resp.put("message", "对不起！您的token 有误！token error");
                        resp.put("code", ResultCode.TOKEN_ERROR);

                        ResponseUtil.returnJson(response, resp);

                        return false;
                    }


                }else {// 可能是伪造的jwt
                    resp.put("message", "对不起！您的token 有误！token error");
                    resp.put("code", ResultCode.TOKEN_ERROR);

                    return false;
                }

            }else {// 被拦截时也给出响应
                resp.put("message", "对不起！您木有权限！请尝试登录");
                resp.put("code", ResultCode.TOKEN_NONE);

                ResponseUtil.returnJson(response, resp);

                return false;
            }
        }




        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
