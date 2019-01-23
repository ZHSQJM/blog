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
 * @date: 2018/12/24 14:33
 * @remarks:    
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole  implements Serializable {

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userid;

    /**
     * 当条数据的状态  1 正常  2 删除
     */
    private Integer disable;

    /**
     * 角色ID
     */
    private Integer roleid;

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