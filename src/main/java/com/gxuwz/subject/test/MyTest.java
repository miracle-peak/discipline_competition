package com.gxuwz.subject.test;

import com.gxuwz.subject.util.MD5Util;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

/**
 * author: 蔡奇峰
 * date: 2020/3/24 22:05
 * @Version V1.0
 **/
public class MyTest {

    /**
     * 配置文件的加密
     */
    @Test
    public void encrypt() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("tale666cqf88miracle6TALE_CQF");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("");
        String password = textEncryptor.encrypt("");
        System.out.println("username:" + username);
        System.out.println("password:" + password);

    }

    @Test
    public void md5(){

        System.out.println(MD5Util.saltEncryption("123456"));

    }

}
