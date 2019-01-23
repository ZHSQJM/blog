package com.zhs.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/17 10:55
 * @package: com.zhs.oss
 * @description:
 */

@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String bucketUrl;
}
