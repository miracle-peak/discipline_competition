package com.gxuwz.subject.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 13:25
 * @Version V1.0
 **/
//@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        // 预检请求处理
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,JSONP");
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept,Authorization,X-CSRF-TOKEN");

            // 是预检请求则设置响应状态并返回
            if(request.getMethod().equals(RequestMethod.OPTIONS.name())) {
                response.setStatus(HttpStatus.OK.value());
                return ;
            }
        }

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
