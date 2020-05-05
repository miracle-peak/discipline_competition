package com.gxuwz.subject.common.annotation;

import java.lang.annotation.*;

/**
 * 设置访问限制注解
 * author: 蔡奇峰
 * date: 2020/4/3 17:32
 * @Version V1.0
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface VisitLimit {
    // 访问次数限制
    int limit();
    // 时间范围
    int rangeTime();
    // 限制指定时间内不能访问
    int expire();



    // 访问次数限制
//    int limit() default 3;
//    // 时间范围
//    int rangeTime() default 5;
//    // 限制指定时间内不能访问
//    int expire() default 60;
}
