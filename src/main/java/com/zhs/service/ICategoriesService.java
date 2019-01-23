package com.zhs.service;

import com.zhs.ResultData;
import com.zhs.entity.Categories;

import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 14:34
 * @package: com.zhs.service
 * @description: 类目的接口层
 */
public interface ICategoriesService {


    ResultData save(Categories categories);


    List<Categories> getAllCategories();
}
