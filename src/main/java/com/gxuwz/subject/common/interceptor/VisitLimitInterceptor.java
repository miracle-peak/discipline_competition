package com.gxuwz.subject.common.interceptor;

import com.gxuwz.subject.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 访问限制拦截器
 * author: 蔡奇峰
 * date: 2020/4/3 17:21
 * @Version V1.0
 **/
public class VisitLimitInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(VisitLimitInterceptor.class);

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // 判断该方法是否含有限制访问注解@VisitLimit
            if (! method.isAnnotationPresent(VisitLimit.class)){
                return true;
            }

            // 获取限制访问注解信息
            VisitLimit visitLimit = method.getAnnotation(VisitLimit.class);

            if (visitLimit == null){
                return true;
            }
            int limit = visitLimit.limit();
            int expireTime = visitLimit.expire();
            int rangeTime = visitLimit.rangeTime();

            // ip + 请求的方法路径作为key
            String key = IpUtil.getIpAddress(request) + request.getRequestURI();

            Integer currentVisit = null;// 当前访问次数
            String value = jedisUtil.getStr(key);

            if (value != null && ! "".equals(value)){
                currentVisit = Integer.valueOf(value);
            }

            if (currentVisit == null){
                // 第一次请求
                jedisUtil.setStr(key, "1", rangeTime);
            }else if (currentVisit < limit){
                // 在指定时间（rangeTime）内请求相同的方法，访问次数+1
                Integer times = currentVisit + 1;
                jedisUtil.setStr(key, times.toString(), rangeTime);
            }else {
                // 超出限制的访问次数，限制访问。即在expireTime内限制访问
                jedisUtil.setStr(key, currentVisit.toString(), expireTime);

                // 要返回的响应消息
                String msg = "小盆友！你访问太频繁了！请在" + expireTime + "秒后再访问";
                int code = ResultCode.VISIT_LIMIT;

                ResponseUtil.responseJson(response, code, msg);
                return false;
            }


        }

        return true;
    }
}
