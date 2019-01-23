package com.zhs.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/9 17:37
 * @package: com.zhs.vo
 * @description: 默认的树形菜单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerTree {

    private  int  id;

    private String perName;

    private List<PerTree> children;
}
