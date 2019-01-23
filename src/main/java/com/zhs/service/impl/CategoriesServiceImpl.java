package com.zhs.service.impl;

import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.dao.CategoriesMapper;
import com.zhs.entity.Categories;
import com.zhs.service.ICategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 14:40
 * @package: com.zhs.service.impl
 * @description:
 */
@Service
public class CategoriesServiceImpl implements ICategoriesService {


    /**
     * 注入类目dao
     */
    @Autowired
    private CategoriesMapper categoriesMapper;

    /**
     * 新增新的类目
     * @param categories
     * @return
     */
    @Override
    public ResultData save(Categories categories) {
        categories.setCreateTime(new Date());
         categoriesMapper.insert(categories);
         return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_ADD_MESSAGE);
    }

    /**
     * 部分也获取所有的类目 用于展示
     * @return
     */
    @Override
    public List<Categories> getAllCategories() {
        return categoriesMapper.selectAll();
    }
}
