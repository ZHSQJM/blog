package com.zhs.service;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.entity.Article;
import com.zhs.entity.SysPermission;
import com.zhs.entity.SysUser;
import com.zhs.vo.SysUserVo;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 17:36
 * @package: com.zhs.service
 * @description:
 */
public interface IUserService {


    SysUser selectUserById(Integer id);

    SysUser selectUserByUserName(String username);

    ResultData saveUser(SysUser users);

    ResultData deleteUser(Integer id);

    ResultData updateUser(SysUser sysUser);

    PageInfo<SysUser> searchUsers(SysUser sysUser, Integer currentPage, Integer pageSize);


}
