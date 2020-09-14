package com.gxuwz.subject.controller;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.common.util.TokenUtil;
import com.gxuwz.subject.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author: 蔡奇峰
 * date: 2020/4/2 14:49
 * @Version V1.0
 **/
@RequestMapping("/upload")
@RestController
@Slf4j
public class UploadController {
    Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 上传文件放在static下需要重新启动系统才可以访问到资源，
     * 因为静态资源打包后放在jar中。
     * TODO 文件放在static的方法只适用于本地，部署到服务器则失效。
     * TODO 因为打包后jar包获取到项目的路径是虚拟的。
     * TODO 而且也不可能将文件下到jar包中。
     *
     * 解决方法：
     * 1.配置虚拟文件路径的映射
     * file:E:\\blog-2020\\src\\main\\resources\\static\\images\\
     * 2.LiveReload ，实现静态文件的热部署，缺点：需要浏览器安装插件
     * 3.热部署，缺点：不能立即查看，项目重启刷新需要时间
     */
    private static final String RELATIVE_PATH = "/src/main/resources/static/file/";

    /** Windows和linux下都可用的文件目录 */
    public static final String separator = File.separator;

    @Autowired
    private IFileService service;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     *  获取七牛云的token
     *
     * @return
     */
    @GetMapping("/getToken")
    public R getToken(){

        return R.ok().data("token", tokenUtil.getRedisToken());
    }

    /**
     * 文件上传
     * @param params
     * @return
     */
    @PostMapping("/upload")
    public R upload(@RequestBody Map<String, Object> params){
        // base64文件字符串
        String base64Str = "";
        // 文件名称
        String name = "";
        if (params.containsKey("baseString")){
            base64Str = (String) params.get("baseString");
        }
        if (params.containsKey("name")){
            name = (String)params.get("name");
        }

        if (StringUtils.isEmpty(base64Str)){
            return R.error().message("文件为空");
        }
        // 文件路径
        String imgFilePath = "";
        // 文件名称
        String fileName = System.currentTimeMillis() + "_" + name;

        String sysPath = System.getProperty("user.dir");
        logger.info("sysPath--->" + sysPath);

        BASE64Decoder decoder = new BASE64Decoder();

        int indexOf = base64Str.indexOf(";");
        // 去掉文件类型取bas64字符串
        base64Str = base64Str.substring(indexOf + 8);

        try{
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            for(int i = 0; i < b.length; ++i){
                if(b[i] < 0){
                    // 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成的文件路径
            imgFilePath = sysPath + RELATIVE_PATH + fileName;
            // 将反斜杠转换为斜杠
            imgFilePath = imgFilePath.replaceAll( "\\\\", "/");

            File upload = new File(sysPath, RELATIVE_PATH);
            if(! upload.exists()){
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
     * 这种下载文件在项目目录的方式只能在本地使用，不能在线上部署使用。
     * 因为springboot打包生成的是jar包，采用jar包部署时，
     * 是不会像war包那样解压生成文件目录，因此是不能把文件下载进jar包内
     *
     * @param
     * @return
     */
    @PostMapping("/download")
    public void download(HttpServletResponse response, @RequestBody Map<String, Object> param) throws Exception {
        // 文件全名
        String fileName = (String) param.get("fileName");
        // 文件名
        String name = fileName.substring(fileName.indexOf("_") + 1);

        log.info("fileName-->" + fileName);

        String contentType = new MimetypesFileTypeMap().getContentType(fileName);

        response.setHeader("Content-type", contentType);
        String sysPath = System.getProperty("user.dir");
        String filePath = sysPath + RELATIVE_PATH + fileName;

        File file = new File(filePath);
        // 如果文件存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
//            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
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
                log.info("Download  successfully!");
            } catch (Exception e) {
                return;
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        }

    }

}
