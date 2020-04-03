package com.gxuwz.subject.common.config;

import com.gxuwz.subject.common.interceptor.JwtInterceptor;
import com.gxuwz.subject.common.interceptor.VisitLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public JwtInterceptor getInterceptor(){
        return new JwtInterceptor();
    }

    @Bean
    public VisitLimitInterceptor getVisitList(){
        return new VisitLimitInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getInterceptor()).excludePathPatterns("/login")
//                .excludePathPatterns("/file")
        .addPathPatterns("/**");

        registry.addInterceptor(getVisitList())
                .addPathPatterns("/**");
    }
}
