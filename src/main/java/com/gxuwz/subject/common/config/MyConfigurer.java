package com.gxuwz.subject.common.config;

import com.gxuwz.subject.common.interceptor.JwtInterceptor;
import com.gxuwz.subject.common.interceptor.VisitLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 *
 * @version V1.0
 * @author: 蔡奇峰
 * date: 2020/3/25 14:54
 **/
@Configuration
public class MyConfigurer implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor getInterceptor() {
        return new JwtInterceptor();
    }

    @Bean
    public VisitLimitInterceptor getVisitList() {
        return new VisitLimitInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getInterceptor()).excludePathPatterns("/user/login")
                // 允许访问文件
                .excludePathPatterns("/file/**")
                // 允许访问swagger
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/webjars/**")
                .addPathPatterns("/**");

        registry.addInterceptor(getVisitList())
                .addPathPatterns("/**");
    }
}
