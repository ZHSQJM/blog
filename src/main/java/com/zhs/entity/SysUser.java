package com.zhs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/7 17:34
 * @remarks:    
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("系统股用户实体类")
public class SysUser implements Serializable {

    /**
     * 用户ID
     */
    @Id
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     * @JsonIgnore 返回的json不带有这个属性
     */

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Size(min=8 ,message = "密码最短为8位")
    private String password;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 性别 0是男  1是女
     */
    @ApiModelProperty("性别")
    private Integer gender;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String truename;

    /**
     * 生日
     */
    @ApiModelProperty("生日")
    private String birthday;

    /**
     * 邮箱
     */
    @ApiModelProperty("EMAIL")
    @Email
    private String email;

    /**
     * 个人简介
     */
    @ApiModelProperty("个人简介")
    private String personalbrief;

    /**
     * 头像地址
     */
    @ApiModelProperty("头像地址")
    private String avatarimgurl;

    /**
     * 最近登录时间
     */
    @ApiModelProperty(hidden = true)
    private String recentlylanded;

    /**
     * 账号创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    /**
     * 账号更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;


    /**
     * 当条数据的状态  1 正常  2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer disable;
}