package com.gxuwz.subject.config;

import com.gxuwz.subject.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 14:48
 * @Version V1.0
 * 注册过滤器
 **/
//@Configuration
public class WebConfig {

//    @Bean
    public FilterRegistrationBean filterRegister(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        MyFilter myFilter = new MyFilter();

        filterRegistrationBean.setFilter(myFilter);

        ArrayList<String> urls = new ArrayList<>();
        urls.add("/*");

        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
