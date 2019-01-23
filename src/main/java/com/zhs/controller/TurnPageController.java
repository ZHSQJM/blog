package com.zhs.controller;

import com.zhs.service.IPermissionService;
import com.zhs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/3 16:11
 * @package: com.zhs.controller
 * @description: 用于页面的跳转控制器
 */

@Controller
public class TurnPageController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;

    /**
     *登录页面
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(){
       return   new ModelAndView("/login");
    }

    /**
     *
     * @return
     */



    /**
     * decription:直接到编辑博客的页面
     * @param
     * @return: java.lang.String
     * @auther: zhs
     * @date: 2018/12/3 16:36
     * @remarks:    
     */
    @GetMapping("orgchart")
    public String editor(){ return "websocket"; }



}
