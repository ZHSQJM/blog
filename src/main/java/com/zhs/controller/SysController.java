package com.zhs.controller;

import com.github.pagehelper.PageInfo;
import com.zhs.annotation.Log;
import com.zhs.entity.SysPermission;
import com.zhs.entity.SysRole;
import com.zhs.entity.SysUser;
import com.zhs.service.IPermissionService;
import com.zhs.service.IRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/3 18:09
 * @package: com.zhs.controller
 * @description:
 */

@Controller
public class SysController {

    @Autowired
    private IRoleService roleService;


    /**
     * 分页显示角色列表
     * @param role
     * @param currentPage
     * @param pageSize
     * @param request
     * @return
     */
 /*   @GetMapping(value="/sys/roles/list.html")
    @RequiresPermissions("sys:roles")
    @Log("获取用户列表")
    public String list(SysRole role, @RequestParam(value="currentPage",defaultValue = "1") int currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10") int pageSize, HttpServletRequest request){
        PageInfo<SysRole> pageInfo = roleService.searchRole(role,currentPage, pageSize);
        request.setAttribute("role", pageInfo);
        return "sys/role/list";
    }
*/
    /**
     * 首页
     * @param request
     * @return
     */
    @GetMapping({"/index","/"})
    public String index(HttpServletRequest request){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        return "/index";
    }


    @GetMapping("main")
    public String main(){
        return "main";
    }

    @GetMapping("websocket")
    public String websocket(){
        return "websocket";
    }
}
