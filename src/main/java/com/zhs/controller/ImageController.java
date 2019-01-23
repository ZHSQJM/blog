package com.zhs.controller;


import com.zhs.ResultData;
import com.zhs.oss.IQiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/3 17:33
 * @package: com.zhs.controller
 * @description: 上傳文件以及圖片的控制器
 */
@Api(value = "测试图片上传",tags = "测试图片上传")
@RestController
@Slf4j
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private IQiniuService qiniuService;

    @PostMapping("/upload")
    @ApiOperation(value="测试图片上传", notes="返回的时图片上上传地址")
    public ResultData uploadImage( MultipartFile file) throws Exception {
        return qiniuService.upload(file);

    }


   /* @Autowired
    private IQiNiuService qiNiuServicel;

   @PostMapping("/upload")
    @ApiOperation(value="测试图片上传", notes="返回的时图片上上传地址")
    public ResultData uploadImage( MultipartFile file) throws IOException {
          String utl= qiNiuServicel.uploadFile(file.getInputStream());
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_UPLOAD,utl);
    }


    @PostMapping("/delete")
    @ApiOperation(value="测试图片删除", notes="返回的时图片是否删除成功")
    public ResultData delete(@RequestParam String key) throws IOException {
              boolean flag= qiNiuServicel.delete(key);
        if(flag==true){
            return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_DELETE);
        }else{
            return ResultData.ofFail(MsgConst.DEFAULT_ERROR_DELETE);
        }
    }*/
}
