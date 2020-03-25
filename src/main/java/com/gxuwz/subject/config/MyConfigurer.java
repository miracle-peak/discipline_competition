package com.gxuwz.subject.config;

import com.gxuwz.subject.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 14:54
 * @Version V1.0
 * 注册拦截器
 **/
@Configuration
public class MyConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new JwtInterceptor());

        registration.excludePathPatterns("/**/login");
    }
}
