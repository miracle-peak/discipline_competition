package com.gxuwz.subject.common.util;

import com.qiniu.util.Auth;

/**
 * @author: 蔡奇峰
 * @date: 2020/5/26 22:34
 **/
public class TokenUtil {



    /** 七牛云密钥*/
    private static final String AK = "hshXCWFhJA87SHY6G0-AVQ16pNEfQFv6ZbkHe7-5";
    private static final String SK = "uqnhNRotTsyjCsvEwOCVc4SZkvehLqn1tee0lWrT";

    private static final String BUCKET = "miraclepeak";


    /**
     *  获取七牛云上传token
     * @return
     */
    public static String getUploadToken(){
        Auth auth = Auth.create(AK, SK);

        return auth.uploadToken(BUCKET);
    }

}
