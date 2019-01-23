package com.zhs.service;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/25 09:37
 * @package: com.zhs.dao
 * @description:
 */
public interface MyMapper<T>  extends Mapper<T>, MySqlMapper<T> {

    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
