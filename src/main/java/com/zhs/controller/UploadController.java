package com.zhs.controller;

import com.zhs.oss.AliyunOSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/18 16:28
 * @package: com.zhs.controller
 * @description:
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    /**
     * 文件上传
     * @param file
     */
    @RequestMapping(value = "uploadBlog",method = RequestMethod.POST)
    public String uploadBlog(MultipartFile file){

        try {

            if(null != file){
                String filename = file.getOriginalFilename();
                if(!"".equals(filename.trim())){
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    //上传到OSS
                    String uploadUrl = AliyunOSSUtil.upload(newFile);
                    System.out.println("上传的地址是"+uploadUrl);
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value = "toUploadBlog",method = RequestMethod.GET)
    public String toUploadBlog(){
        return "upload";
    }


}
