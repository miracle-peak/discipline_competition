package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * author: 蔡奇峰
 * date: 2020/4/2 14:49
 * @Version V1.0
 **/
@RequestMapping("/upload")
@RestController
@Slf4j
public class UploadController {
    Logger logger = LoggerFactory.getLogger(UploadController.class);

    private static final String RELATIVE_PATH = "/src/main/resources/static/file/";

    /**
     * 文件上传
     * @param params
     * @return
     */
    @PostMapping("/upload")
    public R upload(@RequestBody Map<String, Object> params){
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


    /**
     * 下载文件
     * @param fileName
     * @return
     */
    @PostMapping("/download")
    public void download(HttpServletResponse response, @RequestBody Map<String, Object> param) throws Exception {
        String fileName = (String) param.get("fileName");

        String name = fileName.substring(fileName.indexOf("_") + 1);

        log.info("fileName-->" + fileName);
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        String contentType = new MimetypesFileTypeMap().getContentType(fileName);
//
//        response.setHeader("Content-type", contentType);
////        res.setHeader("content-type", "application/octet-stream");
////        res.setContentType("application/octet-stream");
//        response.setHeader("Content-Disposition", "attachment;filename=" + new String((name).getBytes("gbk"), "iso8859-1"));
        String sysPath = System.getProperty("user.dir");
        String filePath = sysPath + RELATIVE_PATH + fileName;


        File file = new File(filePath);
        if (file.exists()) {
            log.info("exists--->");
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 发送给客户端的数据
        /*OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }*/



        /*InputStream f= this.getClass().getResourceAsStream("/static/file/" + fileName);

        response.reset();
        response.setContentType("application/x-msdownload;charset=utf-8");

        String name = fileName.substring(fileName.indexOf("_") + 1);

        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((name).getBytes("gbk"), "iso-8859-1"));//下载文件的名称
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(f);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            bos.close();
            bis.close();
        } catch (final IOException e) {
            try {
                throw e;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

//        return R.ok();
    }

}
