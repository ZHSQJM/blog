package com.zhs.dao;

import com.zhs.entity.SysRole;
import com.zhs.service.MyMapper;

import javax.management.relation.Role;
import java.util.List;

/**
 * decription:
 * @param 
 * @return: 
 * @author : zhs
 * @date: 2018/12/24 14:36
 * @remarks:    
 */
public interface SysRoleMapper extends MyMapper<SysRole> {


    SysRole findRoleByRoleName(String roleName);


    List<Role> findRoleByUserId(int userId);
}