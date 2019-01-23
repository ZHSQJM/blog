package com.zhs.service;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.entity.SysRole;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 14:45
 * @package: com.zhs.service
 * @description:
 */
public interface IRoleService {

    /**
     * 新增角色
     * @param role
     * @return
     */
    ResultData saveRole(SysRole role);

    PageInfo<SysRole> searchRole(SysRole role, Integer currentPage, Integer pageSize);

    ResultData updateRole(SysRole role);

    ResultData getRoleById(Integer id);

    ResultData deleteById(Integer id);

    ResultData findPermissionByRoleId(int roleId);
    /**
     *    //给角色分配权限
     * @param roleid  角色
     * @param arr 权限的集合
     * @return ResultData<XxxxDO>
     */
    ResultData assginPermission(int roleid, String[] arr);
}
