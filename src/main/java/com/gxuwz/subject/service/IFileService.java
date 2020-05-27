package com.gxuwz.subject.service;

import java.util.Map;

/**
 * @author: 蔡奇峰
 * @date: 2020/5/26 20:21
 **/

public interface IFileService {

    /**
     * 上传文件到七牛云
     *
     * @param params
     * @return
     */
    boolean uploadFile(Map<String, Object> params);

    /**
     * 上传文件
     * @param params
     * @return 上传文件标识
     */
    boolean upload(Map<String, Object> params);




}
