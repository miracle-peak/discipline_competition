package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * author: 蔡奇峰
 * date: 2020/4/2 14:49
 * @Version V1.0
 **/
@RequestMapping("/upload")
@RestController
public class UploadController {
    Logger logger = LoggerFactory.getLogger(UploadController.class);

    private static final String RELATIVE_PATH = "/src/main/resources/static/file/";

    @PostMapping("/upload")
    public R upload(@RequestBody Map<String, Object> params, HttpServletRequest request){
        String base64Str = ""; // base64文件字符串
        String name = "";  // 文件名称
        if (params.containsKey("baseString")){
            base64Str = (String) params.get("baseString");
        }
        if (params.containsKey("name")){
            name = (String)params.get("name");
        }

        if (StringUtils.isEmpty(base64Str)){
            return R.error().message("文件为空");
        }
        String imgFilePath = ""; // 文件路径
        String fileName = System.currentTimeMillis() + "_" + name; // 文件名称
        // 本地路径
//        String path = request.getSession().getServletContext().getRealPath("/upload/");
//        logger.info("path--->" + path);

//        String pathResource = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        logger.info("resource--->" + pathResource);

        String sysPath = System.getProperty("user.dir");
        logger.info("sysPath--->" + sysPath);

        /*File projectPath;
        try {
            projectPath = new File(ResourceUtils.getURL("classpath:").getPath());

            logger.info("projectPath--->" + projectPath);
            if (! projectPath.exists()){
                projectPath = new File("");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return R.error().message("上传文件失败");
        }*/

        BASE64Decoder decoder = new BASE64Decoder();

        int indexOf = base64Str.indexOf(";");
        base64Str = base64Str.substring(indexOf + 8); // 去掉文件类型

//        int indexOf = base64Str.indexOf("base64");
//        base64Str = base64Str.substring(indexOf + 7); // 去掉文件类型
        try{
            //Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            for(int i = 0; i < b.length; ++i){
                if(b[i] < 0){
                    //调整异常数据
                    b[i] += 256;
                }
            }
            // 生成的文件路径
            imgFilePath = sysPath + RELATIVE_PATH + fileName;
//            imgFilePath = path + fileName;
            // 将反斜杠转换为斜杠
            imgFilePath = imgFilePath.replaceAll( "\\\\", "/");
            System.out.println("imgPath" + imgFilePath);
            File upload = new File(sysPath, RELATIVE_PATH);
            if(!upload.exists()){
                upload.mkdirs();
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }catch (Exception e){
            System.out.println("错误:" + e);
            return R.error().message("上传文件失败");
        }

        return R.ok().data("fileName", fileName).data("name", name);
    }

}
