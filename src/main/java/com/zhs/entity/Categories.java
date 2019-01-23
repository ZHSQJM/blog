package com.zhs.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 14:34
 * @package: com.zhs.service
 * @description: 类目的接口层
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categories  extends BaseRowModel implements Serializable {

    /**
     * 主键
     */
    @Id
    @ExcelProperty(value = {"序号"},index = 0)
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 类目名称
     */
    @ExcelProperty(value = {"类目名称"},index = 1)
    @ApiModelProperty(value = "类目名称")
    private String categoriesName;

    /**
     * 创建时间
     */
    @ExcelProperty(value = {"创建时间"},index = 2)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 当条数据的状态  1 正常  2 删除
     */
    @ApiModelProperty(hidden = true)
    private Integer disable;

}