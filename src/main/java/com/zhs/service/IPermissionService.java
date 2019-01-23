package com.zhs.service;

import com.zhs.ResultData;
import com.zhs.entity.SysPermission;
import com.zhs.vo.PerTree;

import java.util.List;


/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 14:44
 * @package: com.zhs.service
 * @description:
 */
public interface IPermissionService {

    ResultData savePermission(SysPermission permission);

    /**
     * 查找所有的权限 目的是将其放入到shiro管理中  使每一步请求都收到shiro的拦截
     * @return
     */
    List<SysPermission> findAllPermission();

    /**
     * 根据登录用户查找该用户的所有权限 左侧的菜单
     * @param id
     * @return
     */
    List<SysPermission> findAllPermissionByUserId(Integer id);


    /**
     * 根据登录用户查找该用户的所有权限 目的是在登录的时候调用  在其登陆之后 这些请求都放行
     * @param id
     * @return
     */
    List<SysPermission> findAllPermissionByUserId2(Integer id);


    /**
     * 获取所有的权限  以树的形式展开
     * @return
     */
    ResultData findTree();

    //获取所有的权限  以树的形式展开
    List<SysPermission> list();
}
