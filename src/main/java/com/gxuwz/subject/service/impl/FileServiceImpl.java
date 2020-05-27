package com.gxuwz.subject.service.impl;

import com.gxuwz.subject.common.util.R;
import com.gxuwz.subject.common.util.TokenUtil;
import com.gxuwz.subject.service.IFileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author: 蔡奇峰
 * @date: 2020/5/26 20:27
 **/
@Slf4j
@Service
public class FileServiceImpl implements IFileService {

    private static final String RELATIVE_PATH = "/src/main/resources/static/file/";


    /** Windows和linux下都可用的文件目录 */
    public static final String separator = File.separator;

    /** base64前缀 */
    private static final String base64Prefix = "baseString";

    private static final String nameKey = "name";

    private static final String filePath = "E:\\images\\1.jpg";


    private UploadManager uploadManager = new UploadManager();


    @Override
    public boolean uploadFile(Map<String, Object> params) {

        String key = System.currentTimeMillis() + ".jpg";

        try {
            Response res = uploadManager.put(filePath, key, TokenUtil.getUploadToken());

            System.out.println("response-- isOk->" + res.isOK());
            System.out.println("response-body-->" + res.bodyString());


        } catch (QiniuException e) {
            log.error("上传文件失败：", e);
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    public boolean upload(Map<String, Object> params) {
        // base64文件字符串
        String base64Str = "";
        // 文件名称
        String name = "";
        if (params.containsKey(base64Prefix)){
            base64Str = (String) params.get(base64Prefix);
        }
        if (params.containsKey(nameKey)){
            name = (String)params.get(nameKey);
        }
        if (StringUtils.isEmpty(base64Str)){
            log.error("文件为空");
            return false;
        }
        // 文件路径
        String imgFilePath = "";
        // 文件名称
        String fileName = System.currentTimeMillis() + "_" + name;

        String sysPath = System.getProperty("user.dir");

        BASE64Decoder decoder = new BASE64Decoder();

        int indexOf = base64Str.indexOf(";");
        // 去掉文件类型取bas64字符串
        base64Str = base64Str.substring(indexOf + 8);

        try{
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Str);
            for(int i = 0; i < b.length; ++i){
                if(b[i] < 0){
                    //调整异常数据
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
            log.error("上传文件错误：", e);
            return false;
        }

        return true;
    }
}
