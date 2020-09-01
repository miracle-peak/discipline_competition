package com.gxuwz.subject.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: 蔡奇峰
 * date: 2020/3/25 13:25
 * @Version V1.0
 **/
@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 预检请求处理
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");

        // 是预检请求则设置响应状态并返回
//        if(httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//            httpResponse.setStatus(HttpStatus.OK.value());
//            return ;
//        }

        filterChain.doFilter(request, response);


    }

}
