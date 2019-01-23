package com.zhs.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/6 14:45
 * @package: com.zhs.vo
 * @description: 用于在页面上显示的博客表单类
 */
@Data
public class ArticleVo {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 封面地址
     */
    private  String titleurl;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 类型
     */

    private  String type;

    /**
     * 类别
     */
    private String categories;

    /**
     * 点击量
     */
    private Integer hits;

    /**
     * 评论数
     */
    private Integer commentsnum;

    /**
     * 作者信息
     * @JsonProperty自定义输出的json键名
     */
    @JsonProperty("authorInfo")
    private  String author;

    /**
     * 内容
     */
    private  String content;

    /**
     * 标签
     */
    private  String tags;

    private  String status;

    /**
     * 摘要
     */
    private String summary;

}
