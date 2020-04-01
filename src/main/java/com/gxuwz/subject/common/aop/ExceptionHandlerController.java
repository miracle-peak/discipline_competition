package com.gxuwz.subject.common.aop;

import com.gxuwz.subject.common.util.R;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gxuwz.subject.common.util.ResultCode;

/**
 * 用于未被全局异常处理的错误 如：404
 * author: 蔡奇峰
 * date: 2020/3/31 10:02
 * @Version V1.0
 **/
@RestController
public class ExceptionHandlerController implements ErrorController {


    @GetMapping("/error")
    @ResponseStatus
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        // 错误处理逻辑
        int status = response.getStatus();
        if (status == 404) {
            return new R(ResultCode.NOT_FOUND, "小伙子你有点调皮哦！(*^▽^*)");
        } else if (status == 500) {
            return new R(ResultCode.EXCEPTION_500, "小伙子你麻烦大了！(*^▽^*)");
        } else if (status >= 100 && status < 200) {
            return new R(ResultCode.HTTP_ERROR_100, null);
        } else if (status >= 300 && status < 400) {
            return new R(ResultCode.HTTP_ERROR_300, null);
        } else if (status >= 400 && status < 500) {
            return new R(ResultCode.HTTP_ERROR_400, null);
        } else {
            return new R(ResultCode.SYSTEM_ERROR, "小伙子你麻烦大了！(*^▽^*)");
        }
    }

    @Override
    public String getErrorPath() {

        return "/error";
    }
}
