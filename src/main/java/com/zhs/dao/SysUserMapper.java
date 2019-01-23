package com.zhs.dao;

import com.zhs.entity.SysUser;
import com.zhs.service.MyMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/7 17:42
 * @remarks:    
 */
public interface SysUserMapper extends MyMapper<SysUser> {


    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    SysUser findUserByUserName(String username);


    /**
     * 新增用户返回新增后的Id
     * @param user
     * @return
     */
    int insertReturnId(SysUser user);

    List<SysUser> selectUserList(SysUser user);
}