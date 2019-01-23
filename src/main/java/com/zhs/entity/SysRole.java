package com.zhs.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/24 14:25
 * @remarks:    角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("角色实体类")
public class SysRole implements Serializable {

    /**
     * 主键
     */
    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色的名称")
    @NotBlank(message = "角色的名称不能为空")
    private String rolename;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色的描述")
    @NotBlank(message = "角色的描述不能为空")
    private String roledesc;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createtime;

    /**
     * 当条数据的状态  1 正常  2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer disable;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updatetime;

    /**
     * 备用字段1
     */
    @ApiModelProperty(hidden = true)
    private String ext1;

    /**
     * 备用字段2
     */
    @ApiModelProperty(hidden = true)
    private String ext2;

}