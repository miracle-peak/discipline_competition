package com.gxuwz.subject.common.exception;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.common.util.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理
 * author: 蔡奇峰
 * date: 2020/3/31 9:44
 * @Version V1.0
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public R handleHttpMessageNotReadableException(Exception e) {
        if (e instanceof BindException){
            return new R(StatusCode.EXCEPTION_400, ((BindException)e).getAllErrors().get(0).getDefaultMessage());
        }
        return new R(StatusCode.EXCEPTION_400, e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("没有这个方法：" + e.getMessage());
        return new R(StatusCode.EXCEPTION_NOT_METHOD, "小盆友别乱来哦，not method");
    }

    @ExceptionHandler(RuntimeException.class)
    public R runtimeException(RuntimeException e){
        log.error("运行时异常：" + e.getMessage());

        return R.error();
    }




    /**
     * 其他全局异常在此捕获
     * @param e
     * @return
     */
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable e) {
        if (e instanceof JedisConnectionException) {
            //redis连接异常
            return new R(ResultCode.CONNECTION_ERROR, "系统服务器连接失败！redis connect");
        } else if (e instanceof JedisException) {
            //redis异常
            return new R(ResultCode.REDIS_ERROR, "系统服务器异常请通知管理员！redis error");
        }
        return new R(ResultCode.SYSTEM_ERROR, "系统错误！throwable");
    }*/


}
