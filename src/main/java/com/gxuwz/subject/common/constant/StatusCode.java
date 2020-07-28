package com.gxuwz.subject.common.constant;

/**
 * @author 蔡奇峰
 */
public interface StatusCode {

    int SUCCESS = 666;  // 操作成功
    int ERROR = 2233;   // 操作失败
    int TOKEN_ERROR = 20001; // token 错误
    int TOKEN_NONE = 3333;  // 没有jwt

    // jwt过期
    int JWT_EXPIRE = 13333;
    // jwt异常
    int JWT_EXCEPTION = 5005;

    // 频繁访问
    int VISIT_LIMIT = 11111;

    // 服务器连接失败，如redis连接失败
    int CONNECTION_ERROR = 40001;

    int REDIS_ERROR = 40003;


    // 异常
    int EXCEPTION_400 = 40000;
    // 找不到方法
    int EXCEPTION_NOT_METHOD = 4005;
    // 运行异常
    int EXCEPTION_500 = 5000;


    // HTTP请求错误
    int HTTP_ERROR_100 = 1000;
    int HTTP_ERROR_300 = 3000;
    int HTTP_ERROR_400 = 4000;
    int NOT_FOUND = 4004;
    // 不在100-500的其他错误
    int SYSTEM_ERROR = 7777;


    /**
     * 存全部教师信息的 redis key
     */
    String TEACHER_KEY = "teachers";


}
