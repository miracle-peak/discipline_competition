package com.gxuwz.subject.common.config;

import com.gxuwz.subject.common.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

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
