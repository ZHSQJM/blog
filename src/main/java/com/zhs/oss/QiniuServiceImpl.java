package com.zhs.oss;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.oss.IQiniuService;
import com.zhs.oss.QiNiuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/17 11:03
 * @package: com.zhs.quniu
 * @description:
 */
@Service
public class QiniuServiceImpl implements IQiniuService {

    @Autowired
    private QiNiuProperties qiNiuProperties;

    /**
     * 定义七牛云上传的相关策略
     */
    private StringMap putPolicy;
    @Override
    public ResultData upload(MultipartFile fileList) throws Exception {
        String fileName = fileList.getOriginalFilename();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
        DefaultPutRet putRet = upload(fileList.getBytes(), newFileName);
      String url=qiNiuProperties.getBucketUrl() +"/"+ putRet.key;
      return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_UPLOAD,url);
    }

    public DefaultPutRet upload(byte[] file, String key) throws Exception {
        Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey());
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        Response res = uploadManager.put(file, key, getUpToken(auth, qiNiuProperties.getBucket()));
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
        return putRet;
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(Auth auth, String bucketname) {

        this.putPolicy = new StringMap();
        //putPolicy.put("mimeLimit","image/*");
        return auth.uploadToken(bucketname, null, 3600, putPolicy);

    }
}
