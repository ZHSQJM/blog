package com.zhs.controller;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.constants.MsgConst;
import com.zhs.entity.SysUser;
import com.zhs.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/19 10:04
 * @package: com.zhs.controller
 * @description 用户接口:
 */
@Controller
@RequestMapping("sys/users")
@Slf4j
public class UserController {




    @Autowired
    private IUserService userService;


    /**
     * 分页显示用户列表
     * @param sysUser
     * @param currentPage
     * @param pageSize
     * @param request
     * @return
    */
    @GetMapping(value="list.html")
    @RequiresPermissions("sys:users")
    @Log("获取用户列表")
    public String list(SysUser sysUser,@RequestParam(value="currentPage",defaultValue = "1") int currentPage,
                              @RequestParam(value="pageSize",defaultValue = "10") int pageSize,HttpServletRequest request){

        PageInfo<SysUser> pageInfo = userService.searchUsers(sysUser,currentPage, pageSize);

        System.out.println(pageInfo.getTotal()+"===========");
        request.setAttribute("userInfo", pageInfo);
        return "sys/user/list";
    }

    @ApiOperation(value="更新用户",notes = "更新用户")
    @PostMapping("/updateUser")
    @RequiresPermissions("users:update")
    @Log("更新用户")
    @ResponseBody
    public ResultData updateUser(@Validated SysUser sysUser){

        userService.updateUser(sysUser);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_UPDATE);
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户",notes = "删除用户")
    @PostMapping("/delete/{id}")
    @Log("删除用户")
    @RequiresPermissions("users:delete")
    @ResponseBody
    public ResultData delete(@PathVariable("id") Integer id)  {
        return  userService.deleteUser(id);
    }

    @ApiOperation(value="新增用户",notes = "新增用户")
    @PostMapping("/addUser")
    @Log("新增用户")
    @RequiresPermissions("users:add")
    @ResponseBody
    public ResultData addUser(@Validated SysUser sysUser)  {
        return  userService.saveUser(sysUser);
    }


    @ApiOperation(value="根据主键获取用户",notes = "根据主键获取用户")
    @PostMapping("/getUserById/{id}")
    @Log("获取单个用户")
    @ResponseBody
    public ResultData selectUserById(@PathVariable("id") Integer id)  {
        return  ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,userService.selectUserById(id));
    }
}
