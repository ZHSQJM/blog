package com.zhs.interceptor;


import com.zhs.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 15:57
 * @package: com.zhs.interceptor
 * @description:
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    /**
     * 在每次请求地址（在webconfig中配置的）时  ，第一步都是走这个拦截器 如果请求中带这个token的且token正确  没有过期的话那么就放行
     * 如果没有的话 就不放行
     * @param httpServletRequest
     * @param response
     * @param object
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object) throws Exception {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");


      System.out.println("在每次请求地址（在webconfig中配置的）时  ，第一步都是走这个拦截器  ");
       return  true;
    }

    /**
     * 可以
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
         log.info("执行了方法");
    }

    /**
     * 执行方法之后  更新token的过期时间
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
