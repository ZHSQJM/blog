package com.zhs.controller;

import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.entity.SysPermission;
import com.zhs.entity.SysUser;
import com.zhs.service.IPermissionService;
import com.zhs.service.IUserService;
import com.zhs.utils.DateUtils;
import com.zhs.vo.SysUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/25 10:09
 * @package: com.zhs.controller
 * @description:
 */

@RestController
@Slf4j
@Api(value = "登录注册接口",tags = "登录注册接口")
@RequestMapping("/v1/api")
public class LoginController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    @PostMapping("login")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="用户名",required=true,paramType="form"),
            @ApiImplicitParam(name="password",value="密码",required=true,paramType="form")
    })
    @Log("登录")
    @ApiOperation(value="用户登录接口", notes="用户登录接口")
    public ResultData login(String  username,String password,HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        subject.login(token);
       SysUser sysUser=(SysUser) subject.getPrincipal();
        sysUser.setRecentlylanded( DateUtils.getTime());
        userService.updateUser(sysUser);
        SysUserVo userVo=new SysUserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        List<SysPermission> menus = permissionService.findAllPermissionByUserId(sysUser.getId());
        request.getSession().setAttribute("menus",menus);
        request.getSession().setAttribute("user",userVo);
        return ResultData.ofSuccess("登录成功",userVo);
    }


    @ApiOperation(value="注册用户",notes = "注册用户")
    @PostMapping("register")
    @Log("新增用户")
    public ResultData saveUser(@Validated SysUser user){
        return  userService.saveUser(user);
    }

    @GetMapping("unauthorizedUrl")
    @Log("未授权")
    @ApiOperation(value="未授权", notes="未授权")
    public ResultData unauthorizedUrl(){
        return ResultData.ofFail("没有权限,请联系管理员","403");
    }

    @GetMapping("tologin")
    @Log("未登录直接请求接口")
    @ApiOperation(value="未登录直接请求接口", notes="未登录直接请求接口")
    public ResultData tologin(){
        return ResultData.ofFail("请先登录","401");
    }



}
