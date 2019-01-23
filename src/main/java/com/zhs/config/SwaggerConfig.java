package com.zhs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *@Created with IDEA
 * @author:zhouhuasheng
 * @Date:2018/8/615:19
 * @描述:swagger配置 用来查看api文档的  浏览路径  ip:port/swagger.html
 *     @Profile 用于在开发或者测试的环境下开启
 **/
@Configuration
@EnableSwagger2
@Profile({"dev","test"})
public class SwaggerConfig {

    //访问路径
    //：http://{ip}:{port}/swagger-ui.html

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhs.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title("telnet RESTFUL API 接口测试")
                .description("Telenet API")
                .termsOfServiceUrl("https://blog.csdn.net/lftaoyuan")
                .contact(new Contact(
                        "ZhouHuasheng",
                        "https://blog.csdn.net/lftaoyuan",
                        "2534798858@qq.com"))
                .version("1.0")
                .build();
    }


}
