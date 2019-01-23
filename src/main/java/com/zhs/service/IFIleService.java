package com.zhs.service;

import com.zhs.ResultData;
import org.springframework.web.multipart.MultipartFile;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/12 16:46
 * @package: com.zhs.service
 * @description:
 */
public interface IFIleService {


    /**
     * 上传文件
     * @param file
     * @param userId
     * @param param
     * @return
     */
    ResultData upload(MultipartFile file, int userId, int param);
}
