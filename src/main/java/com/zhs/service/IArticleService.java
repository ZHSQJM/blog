package com.zhs.service;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.entity.Article;
import com.zhs.entity.SysUser;
import com.zhs.vo.ArticleVo;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/3 16:48
 * @package: com.zhs.service
 * @description: 用于博客文章的接口 包括curd操作
 */
public interface IArticleService {

    /**
     * 保存文章
     * @param ariticle
     * @return ResultData
     */
    ResultData save(Article ariticle);


    /**
     *查询文章
     * @param currentPage
     * @param pageSize
     * @return ResultData
     */
    PageInfo<ArticleVo> searchArticles(Article ariticle, Integer currentPage, Integer pageSize);

    ResultData getArticleById(Integer id);

    ResultData delete(Integer id);

}
