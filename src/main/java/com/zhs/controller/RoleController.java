package com.zhs.controller;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.entity.SysRole;
import com.zhs.service.IPermissionService;
import com.zhs.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 15:00
 * @package: com.zhs.controller
 * @description:
 */
@Api(value = "角色类",tags = "角色接口")
@Controller
@Slf4j
@RequestMapping("/sys/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 分页显示角色列表
     * @param role
     * @param currentPage
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping(value="list.html")
    @RequiresPermissions("sys:roles")
    @Log("获取角色列表")
    public String list(SysRole role, @RequestParam(value="currentPage",defaultValue = "1") int currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10") int pageSize, HttpServletRequest request){
        PageInfo<SysRole> pageInfo = roleService.searchRole(role,currentPage, pageSize);
        request.setAttribute("role", pageInfo);
        return "sys/role/list";
    }
    /**
     *
     * @param role
     * @return
     * @throws IOException
     */
    @ApiOperation(value="新增角色",notes = "新增角色")
    @PostMapping("/saveRole")
    @Log("新增角色")
    @ResponseBody
    public ResultData saveRole(@Validated SysRole role) {
        return roleService.saveRole(role);
    }

    @ApiOperation(value="跟新角色",notes = "跟新角色")
    @PostMapping("/updateRole")
    @Log("更新角色")
    @ResponseBody
    public ResultData updateRole(@Validated SysRole role) {
        return roleService.updateRole(role);
    }

    @ApiOperation(value="获取用户",notes = "获取用户")
    @PostMapping("/getRoleById/{id}")
    @ResponseBody
    @Log("获取单个用户")
    public ResultData getRoleById(@PathVariable("id") Integer id){
        return roleService.getRoleById(id);
    }

    @ApiOperation(value="删除角色",notes = "删除角色")
    @PostMapping("/delete/{id}")
    @ResponseBody
    @Log("删除用户")
    public ResultData deleteById(@PathVariable("id") Integer id){
        return roleService.deleteById(id);
    }

    @GetMapping("/permissions")
    @ApiOperation(value="查看角色的权限", notes="返回的是角色的权限信息")
    @ResponseBody
    @Log("查看角色权限")
    public  ResultData findPermissionByRoleId(Integer roleid){
        if(StringUtils.isEmpty(roleid)){
            return  ResultData.ofFail("请选择角色");
        }
        return  roleService.findPermissionByRoleId(roleid);
    }

    /**
     * 给与角色分配权限
     *
     * @param  ids rule id
     * @param  rid rule id
     * @return Result<Xxxx>
     */
    @PostMapping("/assginPermission")
    @ApiOperation(value="给与角色分配权限", notes="返回的是分配是否成功信息")
    @ResponseBody
    @Log("分配角色权限")
    public ResultData assginPermission(@RequestParam(value = "ids[]",required=false)  String[]  ids,@RequestParam(value="rid") Integer rid){
        if(StringUtils.isEmpty(rid)){
            return ResultData.ofFail("请选择角色");
        }
        return roleService.assginPermission(Integer.valueOf(rid),ids);
    }
}
