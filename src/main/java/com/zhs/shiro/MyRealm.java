package com.zhs.shiro;


import com.zhs.entity.SysPermission;
import com.zhs.entity.SysUser;
import com.zhs.service.IPermissionService;
import com.zhs.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * @Created with IDEA
 * @author:周华生
 * @Date:2018/8/17 14:42
 * @描述://自定义的realm
 **/
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * 执行授权
     * @param principalCollection
     * @return List
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("进入了授权+'===========");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<String,Object>(1000);

        List<SysPermission> list = permissionService.findAllPermissionByUserId2(sysUser.getId());
        System.out.println(list.size());
       for(SysPermission tp:list){
           System.out.println(tp);
           info.addStringPermission(tp.getPerms());
       }
        return info;
    }

    /**
     *执行认证逻辑
     * @param authenticationToken
     * @return List
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        System.out.println("+进入了认证'===========");
       String name= token.getUsername();
        SysUser sysUser =userService.selectUserByUserName(name);
        //若存在，将此用户存放到登录认证info中，无需自己做密码对比Shiro会为我们进行密码对比校验
            ByteSource credentialsSalt = ByteSource.Util.bytes(sysUser.getUsername());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), credentialsSalt, getName());
            return authenticationInfo;

    }
}
