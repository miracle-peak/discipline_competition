package com.gxuwz.subject.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * author: 蔡奇峰
 * date: 2020/4/17 16:06
 **/
@Component
@Aspect
public class LoginLog {


    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.gxuwz.subject.*Controller.login(..))")//断点位置
    public void loginWebLog() {
    }

    @Pointcut("execution(public * com.gxuwz.subject.service.*Services.*(..))")//断点位置
    public void operateWebLog() {
    }

}
