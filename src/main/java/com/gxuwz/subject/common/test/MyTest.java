package com.gxuwz.subject.common.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gxuwz.subject.common.util.TokenUtil;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.springframework.util.Base64Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: 蔡奇峰
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
        String password = textEncryptor.encrypt("123456");
        System.out.println("username:" + username);
        System.out.println("password:" + password);

    }

    @Test
    public void md5(){
        String token = TokenUtil.getUploadToken();
        System.out.println("token-->" + token);
        String substring = token.substring(token.lastIndexOf(":") + 1);
        System.out.println("sub-->" + substring);

        byte[] decode = Base64Utils.decode(substring.getBytes());

        System.out.println("----");

        String s = new String(decode);

        System.out.println("s-->" + s);

//        System.out.println(MD5Util.saltEncryption("123456"));

    }


    @Test
    public void MyTest(){
        String result = "[{'channelID':'0ed3e782'},{'channelID':'112279d92e3aafbf'},]";

        JSONArray objects = JSONArray.parseArray(result);

        System.out.println(objects.size() + " object--->" + objects);

        ArrayList<JSONObject> listPoint = new ArrayList<JSONObject>();


        System.out.println("array-->" + objects);

        for (int i = 0; i < objects.size(); i++) {
            listPoint.add(objects.getJSONObject(i));
        }

        System.out.println(listPoint.toString() + "------");


        System.out.println(objects.get(0));
    }


    @Test
    public void test(){
        // 使用双冒号::来构造非静态函数引用
        String content = "Hello JDK8";
        Function<Integer, String> func = content::substring;
        String result = func.apply(1);
        System.out.println(result);

        Map map = new HashMap<>();

        map.put(null, "a");
        map.put(null, "b");

        System.out.println("map---->" + map.get(null));

    }


    @Test
    public void testFor(){
        for (;;){
            int i = 2;
            System.out.println("i--->" + i);
            i++;
            return;
        }
    }


}
