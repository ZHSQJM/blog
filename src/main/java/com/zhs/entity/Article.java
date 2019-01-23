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

/**
 * decription:
 * @param 
 * @return: 
 * @author: zhs
 * @date: 2018/12/7 14:22
 * @remarks:    
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    /**
     * 主键
     */
    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;


    /**
     * 标题
     */ @ApiModelProperty(value = "文章标题")
    private String title;

    /**
     * 封面地址
     */
    @ApiModelProperty("封面地址")
    private String titleurl;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern="yyyy-MM-dd ",timezone = "GMT+8")
    private Date createtime;

    /**
     * 作者信息
     */
    @ApiModelProperty(value = "用户的ID")
    private Integer authorid;

    /**
     * 类型  1 原创  2 转载 3翻译
     */
    @ApiModelProperty(value = "博客类型")
    private Integer type;

    /**
     * 标签  java springboot  linux
     */
    @ApiModelProperty(value = "文章标签")
    private String tags;

    /**
     * 状态  1 已发布  2草稿
     */
    @ApiModelProperty(value = "文章状态")
    private Integer status;

    /**
     * 类目  1 后台  2 前端  3 运维 4 数据库
     */
    @ApiModelProperty(value = "类目")
    private Integer categories;

    /**
     * 点击量
     */
    @ApiModelProperty(hidden = true)
    private Integer hits;

    /**
     * 评论数·
     */
    @ApiModelProperty(hidden = true)
    private Integer commentsnum;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;


    /**
     * 当条数据的状态  1 正常  2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer disable;

    /**
     * 摘要
     */
    private String summary;
}