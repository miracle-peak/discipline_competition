package com.gxuwz.subject.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 13:34
 * @Version V1.0
 **/
public class ResponseUtil {


    /**
     *提供响应
     * @param response
     * @param value
     */
    public static void returnJson(HttpServletResponse response, Object value){

        OutputStream outputStream = null;

        JSONObject object = new JSONObject();
        object.put("result", value);
        String result = object.toJSONString();

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");

            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            response.setHeader("Access-Control-Allow-Headers", "*");

            outputStream = response.getOutputStream();
            outputStream.write(result.getBytes());

            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



    }


}
