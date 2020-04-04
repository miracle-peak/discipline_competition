package com.gxuwz.subject.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * author: 蔡奇峰
 * date: 2020/3/25 13:34
 * @Version V1.0
 **/
public class ResponseUtil {

    /**
     * 提供响应
     * @param response
     * @param code
     * @param msg
     */
    @CrossOrigin
    public static void responseJson(HttpServletResponse response, int code, String msg) {

        OutputStream outputStream = null;
//
//        JSONObject object = new JSONObject();

//        object.put("data", value);
        String result = JSON.toJSONString(new R(code, msg));

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");

            outputStream = response.getOutputStream();
            outputStream.write(result.getBytes());

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
    /**
     *提供响应
     * @param response
     * @param value
     */
    @CrossOrigin
    public static void returnJson(HttpServletResponse response, Object value){

        OutputStream outputStream = null;

        JSONObject object = new JSONObject();
        object.put("data", value);
        String result = object.toJSONString();

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");

//            response.addHeader("Access-Control-Allow-Origin", "*");
//            response.addHeader("Access-Control-Allow-Methods", "*");
//            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//            response.setHeader("Access-Control-Allow-Headers", "*");

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
