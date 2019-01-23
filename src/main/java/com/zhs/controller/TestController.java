package com.zhs.controller;

import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.entity.Categories;
import com.zhs.utils.ExcelUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/19 11:17
 * @package: com.zhs.controller
 * @description:
 */

@RestController
@RequestMapping("/v1/api/test")
@Slf4j
public class TestController {


    @GetMapping("")
    public ResultData testWebsocket(){
        Date date=new Date();
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,date);
    }

    @GetMapping("/download")
    @ApiOperation(value="新增类目",notes = "新增类目",produces ="application/octet-stream" )
    public ResponseEntity<InputStreamResource> get( HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = "20181219154938.xls";
        if (fileName != null) {
            //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
            String realPath = "C:\\Users\\Iron man\\Documents\\Tencent Files\\2534798858\\FileRecv";;
            File file = new File(realPath, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + fileName);// 设置文件名
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
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    @GetMapping("/excel")
    public void test(HttpServletResponse response){

        List<Categories> list=new ArrayList<>();
        Categories categories1=new  Categories(1,"java",new Date(),1);
        Categories categories2=new  Categories(1,"java",new Date(),1);
        Categories categories3=new  Categories(1,"java",new Date(),1);
        list.add(categories1);
        list.add(categories2);
        list.add(categories3);

         ExcelUtils.export(list,response,Categories.class);
    }
}

