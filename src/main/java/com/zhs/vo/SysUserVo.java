package com.zhs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/19 09:53
 * @package: com.zhs.vo
 * @description: 用于用户的VO类
 */
@Data
public class SysUserVo {

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 手机号码
     */
    private String phone;

    /**
     * 性别
     */
    private Integer gender;


    /**
     * 生日
     */
    private String birthday;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否被禁用
     */
    private Integer disable;
    /**
     * 个人简介
     * private String personalbrief;
     */


    /**
     * 头像地址
     */
    private String avatarimgurl;

    /**
     * 最近登录时间
     */
    private String recentlylanded;

    /**
     * 账号创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    /**
     * 账号更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatetime;
}
