package com.zhs.oss;

import com.zhs.ResultData;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.IOException;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/17 11:03
 * @package: com.zhs.quniu
 * @description:
 */
public interface IQiniuService {


    ResultData upload(MultipartFile fileList) throws Exception;
}
