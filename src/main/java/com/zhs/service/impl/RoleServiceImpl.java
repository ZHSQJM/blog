package com.zhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.dao.SysPermissionMapper;
import com.zhs.dao.SysRoleMapper;
import com.zhs.dao.SysRolePermissionMapper;
import com.zhs.entity.SysPermission;
import com.zhs.entity.SysRole;
import com.zhs.entity.SysRolePermission;
import com.zhs.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 14:50
 * @package: com.zhs.service.impl
 * @description:
 */

@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public ResultData saveRole(SysRole role) {

        SysRole sysRole= sysRoleMapper.findRoleByRoleName(role.getRolename());
        if(sysRole!=null){
           return ResultData.ofFail("角色名"+sysRole.getRolename()+"已经存在");
        }else{
            role.setCreatetime(new Date());
            role.setDisable(1);
            role.setUpdatetime(new Date());
            sysRoleMapper.insert(role);
            return  ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_ADD_MESSAGE,role.getRolename());
        }
    }

    @Override
    public PageInfo<SysRole> searchRole(SysRole role, Integer currentPage, Integer pageSize) {
        role.setDisable(1);
        PageHelper.startPage( currentPage,  pageSize);
        List<SysRole> list= sysRoleMapper.select(role);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ResultData updateRole(SysRole role) {
        role.setUpdatetime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(role);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_UPDATE);
    }

    @Override
    public ResultData getRoleById(Integer id) {
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,sysRoleMapper.selectByPrimaryKey(id));
    }

    @Override
    public ResultData deleteById(Integer id) {
        SysRole role=sysRoleMapper.selectByPrimaryKey(id);
        role.setDisable(0);
        sysRoleMapper.updateByPrimaryKeySelective(role);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_DELETE);
    }

    @Override
    public ResultData findPermissionByRoleId(int roleId) {
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,sysPermissionMapper.loadByRoleId(roleId));
    }

    @Override
    public ResultData assginPermission(int roleid, String[] arr) {
        //将该角色的所有权限查找出来
        List<SysPermission> list = sysPermissionMapper.loadByRoleId(roleid);

        //先将改角色的所有权限删除
        if (list.size() > 0 && list != null) {
            for (SysPermission tp : list) {
                SysRolePermission ttRolePermission = new SysRolePermission();
                ttRolePermission.setPermissionid(tp.getId());
                ttRolePermission.setRoleid(roleid);
                sysRolePermissionMapper.delete(ttRolePermission);
            }
        }
        System.out.println(arr + "---");
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                SysRolePermission trp = new SysRolePermission();
                trp.setCreatetime(new Date());
                trp.setRoleid(roleid);
                trp.setUpdatetime(new Date());
                trp.setDisable(1);
                trp.setPermissionid(Integer.valueOf(arr[i]));
                sysRolePermissionMapper.insert(trp);
            }
        }
        return ResultData.ofSuccess("分配成功");
    }
}
