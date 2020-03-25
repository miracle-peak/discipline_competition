package com.gxuwz.subject.util;

public interface ResultCode {

    int SUCCESS = 666;  // 操作成功
    int ERROR = 2233;   // 操作失败
    int TOKEN_ERROR = 20001; // token 错误
    int TOKEN_NONE = 3333;  // 没有jwt

    int CONNECTION_ERROR = 40001; // 服务器连接失败，如redis连接失败



}
