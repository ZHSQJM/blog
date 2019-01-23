package com.zhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.dao.SysUserMapper;
import com.zhs.dao.SysUserRoleMapper;
import com.zhs.entity.SysUser;
import com.zhs.entity.SysUserRole;
import com.zhs.service.IUserService;
import com.zhs.vo.SysUserVo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 17:41
 * @package: com.zhs.service.impl
 * @description:
 */

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据主键获取用户
     * @param id
     * @return
     */
    @Override
    public SysUser selectUserById(Integer id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        SysUserVo sysUserVo=new SysUserVo();
        BeanUtils.copyProperties(sysUser,sysUserVo);
        return sysUser;
    }


    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public SysUser selectUserByUserName(String username) {
        return sysUserMapper.findUserByUserName(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData saveUser(SysUser users) {

        SysUser user1=sysUserMapper.findUserByUserName(users.getUsername());
        if(user1 !=null){
            return ResultData.ofFail("用户"+users.getUsername()+"已存在");
        }
        SysUser user=new SysUser();
        user.setPhone(users.getPhone());
       if( sysUserMapper.selectOne(user) !=null){
           return ResultData.ofFail("手机号"+users.getPhone()+"已存在");
       }
        ByteSource salt = ByteSource.Util.bytes(users.getUsername());
       //将用户注册使用的密码 通过MD5加密1024次得到新用户
        /**
         * MD5加密：
         * 使用SimpleHash类对原始密码进行加密。
         * 第一个参数代表使用MD5方式加密
         * 第二个参数为原始密码
         * 第三个参数为盐值，即用户名
         * 第四个参数为加密次数
         * 最后用toHex()方法将加密后的密码转成String
         *
         */
        String newPs = new SimpleHash("MD5", users.getPassword(), salt, 1024).toHex();
        users.setPassword(newPs);
        users.setCreatetime(new Date());
        users.setUpdatetime(new Date());
        users.setDisable(1);

      sysUserMapper.insertReturnId(users);

        /**
         * 新注册的用户默认给他角色2 test的用户
         */

        SysUserRole sysUserRole= new SysUserRole();
        sysUserRole.setUserid(users.getId());
        sysUserRole.setRoleid(2);
        sysUserRole.setCreatetime(new Date());
        sysUserRole.setUpdatetime(new Date());
        sysUserRole.setDisable(1);
        sysUserRoleMapper.insert(sysUserRole);
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_ADD_MESSAGE);
    }

    @Override
    public ResultData deleteUser(Integer id) {
        SysUser sysUser=sysUserMapper.selectByPrimaryKey(id);
        sysUser.setDisable(0);
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_DELETE);
    }

    @Override
    public ResultData updateUser(SysUser sysUser) {
        sysUser.setUpdatetime(new Date());
        /**
         * 获取一开始的用户的密码
         * 获取用户输入的密码  如果没有修改密码 那么oldpassword与prin的密码是一致的
         * 否则如果不一致 那么将用户输入的密码 使用用户名加密
         */
        String oldpassword = sysUserMapper.selectByPrimaryKey(sysUser.getId()).getPassword();
        String  printpassword=sysUser.getPassword();
        if(!oldpassword.equals(printpassword)){
            ByteSource salt = ByteSource.Util.bytes(sysUser.getUsername());
            String newPs = new SimpleHash("MD5", sysUser.getPassword(), salt, 1024).toHex();
            sysUser.setPassword(newPs);
        }
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_UPDATE);
    }

    @Override
    public  PageInfo<SysUser> searchUsers(SysUser sysUser,Integer currentPage, Integer pageSize) {

        PageHelper.startPage( currentPage,  pageSize);
        List<SysUser> list= sysUserMapper.selectUserList(sysUser);

        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
