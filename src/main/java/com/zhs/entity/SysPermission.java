package com.zhs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/24 14:22
 * @remarks:   资源实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission  implements Serializable {

    /**
     * 主键
     */
    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称")
    @NotBlank(message = "资源名称不能为空")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("资源的ICON")
    private String icon;

    /**
     * 权限码
     */
    @ApiModelProperty("权限码")
    @NotBlank(message = "权限码不能为空")
    private String perms;

    /**
     * 请求路径
     */
    @ApiModelProperty("请求路径")
    @NotBlank(message = "请求路径不能为空")
    private String resurl;

    /**
     * 类型  1 菜单 2 按钮 3 特殊API(比如更具用户获取他的权限) 4 外链
     */
    @ApiModelProperty("权限类型")
    @NotBlank(message = "权限类型不能为空")
    private Integer type;

    /**
     * 父类的Id
     */
    @ApiModelProperty("父菜单ID")
    private Integer parentid;

    /**
     * 序号
     */
    @ApiModelProperty("序号")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    /**
     * 当条数据的状态  1 正常  2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer disable;

    /**
     * 当前节点下的节点
     */
    @ApiModelProperty(hidden = true)
    private List<SysPermission> list;


}