package com.gxuwz.subject.util;

public interface ResultCode {

    int SUCCESS = 666;  // 操作成功
    int ERROR = 2233;   // 操作失败
    int TOKEN_ERROR = 20001; // token 错误
    int TOKEN_NONE = 3333;  // 没有jwt

    int CONNECTION_ERROR = 40001; // 服务器连接失败，如redis连接失败


    // 异常
    int EXCEPTION_400 = 40000;
    int EXCEPTION_NOT_METHOD = 4005; // 找不到方法
    int EXCEPTION_500 = 5000;       // 运行异常


    // HTTP请求错误
    int HTTP_ERROR_100 = 1000;
    int HTTP_ERROR_300 = 3000;
    int HTTP_ERROR_400 = 4000;
    int NOT_FOUND = 4004;
    int SYSTEM_ERROR = 7777; // 不在100-500的其他错误




}
