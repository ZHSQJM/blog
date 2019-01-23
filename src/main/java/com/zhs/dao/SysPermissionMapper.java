package com.zhs.dao;

import com.zhs.entity.SysPermission;
import com.zhs.service.MyMapper;

import java.util.List;

/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/24 14:35
 * @remarks:    
 */
public interface SysPermissionMapper extends MyMapper<SysPermission> {


    /**
     * 根据登录的用户ID查找用户所有的权限
     * @param id
     * @return
     */
    List<SysPermission> loadAllPer(Integer id);

    List<SysPermission> selectByParentId(Integer id);

    List<SysPermission> loadByRoleId(Integer id);
}