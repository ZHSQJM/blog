package com.zhs.service.impl;

import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.dao.SysPermissionMapper;
import com.zhs.entity.SysPermission;
import com.zhs.service.IPermissionService;
import com.zhs.vo.PerTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/24 15:30
 * @package: com.zhs.service.impl
 * @description:
 */

@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public ResultData savePermission(SysPermission permission) {

        SysPermission sysPermission=new SysPermission();
        sysPermission.setPerms(permission.getPerms());
        if(permissionMapper.selectOne(sysPermission) !=null){
           return ResultData.ofFail("权限码已存在,请修改权限在保存");
        }
        sysPermission.setPerms(null);
        sysPermission.setName(permission.getName());
        if(permissionMapper.selectOne(sysPermission) !=null){
            return ResultData.ofFail("菜单名已存在,请修改菜单名称在保存");
        }
            permission.setCreatetime(new Date());
            permission.setUpdatetime(new Date());
            permission.setDisable(1);
            permissionMapper.insert(permission);
            return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_ADD_MESSAGE,permission.getName());
    }

    @Override
    public List<SysPermission> findAllPermission() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<SysPermission> findAllPermissionByUserId(Integer id) {

        List<SysPermission> list=null;
       List<SysPermission> allPerlist= permissionMapper.loadAllPer(id);
       for (SysPermission sysPermission:allPerlist){
           System.out.println(sysPermission);
       }
        //获取所有的一级菜单
        List<SysPermission> permissionList = permissionMapper.loadAllPer(id).stream().filter(e->e.getParentid()==0 &&e.getType()==1).collect(Collectors.toList());

        for(SysPermission sysPermission:permissionList){
            list = new ArrayList<>(16);
            for(SysPermission allPer:allPerlist){
                if(allPer.getParentid().equals(sysPermission.getId())){
                    list.add(allPer);
                     }
            }
            sysPermission.setList(list);
        }
        System.out.println("最后的"+permissionList);
        return permissionList;
    }

    /**
     * 获取用户的所有权限放入到shiro管理
     * @param id
     * @return
     */
    @Override
    public List<SysPermission> findAllPermissionByUserId2(Integer id) {
        return permissionMapper.loadAllPer(id);
    }

    @Override
    public ResultData findTree() {
        List<PerTree> all=new ArrayList<>();
        List<PerTree> allnull=new ArrayList<>();
        //获取所有的一级菜单
        List<SysPermission> permissionList = permissionMapper.selectAll().stream().filter(e->e.getParentid()==0 &&e.getType()==1).collect(Collectors.toList());
        for(SysPermission tp:permissionList){
            PerTree perTree=new PerTree();
            perTree.setId(tp.getId());
            perTree.setPerName(tp.getName());
            //获取下面所有的二级菜单
            List<SysPermission>  list2=permissionMapper.selectByParentId(tp.getId());
            if(list2.size()>0&&list2!=null){
                List<PerTree> all2=new ArrayList<>();
                for(SysPermission tp2:list2){
                    List<PerTree> all5=new ArrayList<>();
                    PerTree perTree2=new PerTree();
                    perTree2.setId(tp2.getId());
                    perTree2.setPerName(tp2.getName());
                    List<SysPermission>  list3=permissionMapper.selectByParentId(tp2.getId());
                    if(list3.size()>0&&list3!=null){
                        List<PerTree> all3=new ArrayList<>();
                        for(SysPermission tp3:list3){
                            PerTree perTree3=new PerTree();
                            perTree3.setId(tp3.getId());
                            perTree3.setPerName(tp3.getName());
                            List<PerTree> all4=new ArrayList<>();
                            perTree3.setChildren(all4);
                            all3.add(perTree3);
                        }
                        perTree2.setChildren(all3);
                    }else{
                        perTree2.setChildren(all5);
                    }
                    all2.add(perTree2);
                }
                perTree.setChildren(all2);
            }else{
                perTree.setChildren(allnull);
            }

            all.add(perTree);
        }
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,all);
    }

    @Override
    public List<SysPermission> list() {
        List<SysPermission> permissionList = permissionMapper.selectAll().stream().filter(e->e.getParentid()==0 &&e.getType()==1).collect(Collectors.toList());
        for(SysPermission sysPermission:permissionList){
           List<SysPermission> secondlist= permissionMapper.selectByParentId(sysPermission.getId());
            if(secondlist.size()>0 && secondlist!= null){
               for(SysPermission sysPermission1:secondlist){
                   List<SysPermission> thirdlist = permissionMapper.selectByParentId(sysPermission1.getId());
                   sysPermission1.setList(thirdlist);
               }
               sysPermission.setList(secondlist);
            }
        }
        return permissionList;
    }
}

