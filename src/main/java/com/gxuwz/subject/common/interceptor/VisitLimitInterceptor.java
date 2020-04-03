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
import java.util.HashMap;
import java.util.Map;

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

            String key = IpUtil.getIpAddress(request) + request.getRequestURI();

            logger.info("ip-->" + key);

            Integer currentVisit = null;// 当前访问次数
            String value = jedisUtil.getStr(key);

            if (value != null && ! "".equals(value)){
                currentVisit = Integer.valueOf(value);
            }

            if (currentVisit == null){
                jedisUtil.setStr(key, "1", rangeTime);
            }else if (currentVisit < limit){
                Integer times = currentVisit + 1;
                jedisUtil.setStr(key, times.toString(), rangeTime);
            }else {
                Map<String, Object> info = new HashMap<>();
                jedisUtil.setStr(key, currentVisit.toString(), expireTime);

                info.put("message", "小盆友！你访问太频繁了！请在" + expireTime + "秒后再访问");
                info.put("code", ResultCode.VISIT_LIMIT);

                ResponseUtil.returnJson(response, info);
                return false;
            }


        }

        return true;
    }
}
