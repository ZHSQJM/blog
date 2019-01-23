package com.zhs.config;

import com.zhs.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 16:01
 * @package: com.zhs.config
 * @description: web配置类  用来配置拦截器过滤器 和监听器
 */
@SpringBootConfiguration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Autowired
    private TokenInterceptor tokenInterceptor;

    /**
     * 配置拦截器  用于拦截每次的api/**的请求  以验证token
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/**");
    }


}
