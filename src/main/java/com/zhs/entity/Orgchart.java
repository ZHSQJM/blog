package com.zhs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class Orgchart implements Serializable {

    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 职位
     */
    private String name;

    /**
     * 职位在职人员
     */
    private String title;


    /**
     * 上一级人员
     */
    private Integer pid;

    /**
     * 是否有效
     */
    @JsonIgnore
    private Integer disable;

    /**
     * 下一集职位
     */
    private List<Orgchart> children;
}