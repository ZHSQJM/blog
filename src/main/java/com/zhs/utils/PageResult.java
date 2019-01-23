package com.zhs.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/11 14:30
 * @package: com.zhs.utils
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageResult {

    private Long total;
    private List rows;

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }
}
