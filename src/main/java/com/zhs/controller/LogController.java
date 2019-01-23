package com.zhs.controller;

import com.github.pagehelper.PageInfo;
import com.zhs.entity.SysLog;
import com.zhs.entity.SysUser;
import com.zhs.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/14 14:30
 * @package: com.zhs.controller
 * @description:
 */

@Controller
@RequestMapping("/sys/log")
public class LogController {

    @Autowired
    private ISysLogService logService;

    @GetMapping("list.html")
    public String list(SysLog syslog,@RequestParam(value="currentPage",defaultValue = "1") int currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10") int pageSize,HttpServletRequest request){
        PageInfo<SysLog> pageInfo = logService.searchLog( syslog,currentPage, pageSize);
        System.out.println(pageInfo.getTotal()+"===========");
        System.out.println(pageInfo);
        request.setAttribute("logInfo", pageInfo);
        return "sys/log/list";
    }
}
