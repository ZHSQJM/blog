package com.zhs.controller;

import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.constants.MsgConst;
import com.zhs.entity.SysPermission;
import com.zhs.entity.SysRole;
import com.zhs.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 15:47
 * @package: com.zhs.controller
 * @description:
 */

@Api(value = "权限的增删改查",tags = "权限的接口")
@Controller
@RequestMapping("/sys/permissions")
@Slf4j
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @ApiOperation(value="新增权限",notes = "新增权限")
    @PostMapping("/savePermission")
    @Log("新增权限")
    @ResponseBody
    public ResultData savePermission(@Validated SysPermission permission) throws IOException {
        return permissionService.savePermission(permission);
    }

    @ApiOperation(value="根据用户Id查找该用户的所有菜单  以树的形式展示",notes = "根据用户Id查找该用户的所有菜单  以树的形式展示")
    @GetMapping("/findAllPermissionByUserId/{id}")
    @Log("查找用户的菜单")
    @ResponseBody
    public ResultData findAllPermissionByUserId(@PathVariable("id") Integer id) throws IOException {
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,permissionService.findAllPermissionByUserId(id));
    }
    @ApiOperation(value="根据用户Id查找该用户的所有菜单  以树的形式展示",notes = "根据用户Id查找该用户的所有菜单  以树的形式展示")
    @GetMapping("/findtree")
    @ResponseBody
    @Log("查找用户的菜单")
    public ResultData findTree() {
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,permissionService.findTree());
    }
    /**
     * 用来获取所有的菜单树形结构
     */
    @GetMapping("/findAllPers")
    @ResponseBody
    @ApiOperation(value="用来获取所有的菜单树形结构", notes="用来获取所有的菜单树形结构")
    public ResultData findAllPers(){
        log.info("进入了属性接口");
        return permissionService.findTree();
    }


    /**
     * 用来获取所有的菜单树形结构
     */
    @GetMapping("/list.html")
    @ApiOperation(value="用来获取所有的菜单树形结构", notes="用来获取所有的菜单树形结构")
    public String list(HttpServletRequest request){
        List<SysPermission> list = permissionService.list();
        request.setAttribute("list",list);
        return "sys/per/list";
    }
}
