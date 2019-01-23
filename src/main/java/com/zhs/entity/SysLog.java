package com.zhs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 操作者
     */
    private String username;

    /**
     * 运行了是什么模块
     */
    private String operation;

    /**
     * 耗时
     */
    private Integer time;

    /**
     * 请求了什么方法
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 请求的IP
     */
    private String ip;

    /**
     * 请求的时间
     */
    private Date gmtcreate;

    private Integer disable;


}