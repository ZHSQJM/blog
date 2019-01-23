package com.zhs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/24 14:28
 * @remarks:    中间表 角色和权限
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermission  implements Serializable {

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleid;

    /**
     * 当条数据的状态  1 正常  2 删除
     */
    private Integer disable;

    /**
     * 权限ID
     */
    private Integer permissionid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 备用字段
     */
    private String ext1;

    /**
     * 备用字段
     */
    private String ext2;
    
}