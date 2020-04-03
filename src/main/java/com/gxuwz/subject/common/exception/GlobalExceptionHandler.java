package com.gxuwz.subject.common.exception;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.common.util.ResultCode;
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
public class GlobalExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public R handleHttpMessageNotReadableException(Exception e) {
        if (e instanceof BindException){
            return new R(ResultCode.EXCEPTION_400, ((BindException)e).getAllErrors().get(0).getDefaultMessage());
        }
        return new R(ResultCode.EXCEPTION_400, e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new R(ResultCode.EXCEPTION_NOT_METHOD, "小盆友别乱来哦，not method");
    }

    /**
     * 其他全局异常在此捕获
     * @param e
     * @return
     */
   /* @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable e) {
        if (e instanceof JedisConnectionException) {
            //redis连接异常
            return new R(ResultCode.REDIS_CONNECT_ERROR);
        } else if (e instanceof JedisException) {
            //redis异常
            return new R(ResultCode.REDIS_ERROR);
        }
        return new R(ResultCode.SYSTEM_ERR, null);
    }
*/


}
