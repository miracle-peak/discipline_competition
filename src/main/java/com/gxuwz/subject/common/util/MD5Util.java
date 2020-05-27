package com.gxuwz.subject.common.util;

import org.springframework.util.DigestUtils;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 11:30
 *
 * @Version V1.0
 **/
public class MD5Util {

    // 盐
    private static final String SALT = "tale_salt";

    /**
     * md5加密
     * @param str
     * @return
     */
    public static String md5(String str){
        String asHex = DigestUtils.md5DigestAsHex(str.getBytes());

        return asHex;
    }

    /**
     * md5加盐加密
     * @param password
     * @return
     */
    public static String saltEncryption(String password) {
        password = SALT.charAt(2) + password + SALT.charAt(3) + SALT.charAt(1);

        String str = md5(password);

        return md5(str);
    }
}
