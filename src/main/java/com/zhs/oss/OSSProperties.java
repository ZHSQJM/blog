package com.zhs.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/18 15:39
 * @package: com.zhs.oss
 * @description: 阿里云配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "alioss")
public class OSSProperties {

    private String bucketName;
    private String endPoint;
    private String baseOssUri;
    private String accessKeyId;
    private String accessKeySecret;
    private  String host;

}
