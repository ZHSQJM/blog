package com.zhs.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.constants.MsgConst;
import com.zhs.dao.ArticleMapper;
import com.zhs.dao.CategoriesMapper;
import com.zhs.dao.SysUserMapper;
import com.zhs.entity.Article;
import com.zhs.entity.SysUser;
import com.zhs.enums.AriticleStatusEnum;
import com.zhs.enums.AriticleTypeEnum;
import com.zhs.service.IArticleService;


import com.zhs.service.IUserService;
import com.zhs.vo.ArticleVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/3 17:04
 * @package: com.zhs.service.impl
 * @description: 具体的文章接口实现类
 */

@Service
public class ArticleServiceImpl implements IArticleService {


    /**
     * 注入文章操作dao
     */
    @Autowired
    private ArticleMapper ariticleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CategoriesMapper categoriesMapper;

    /**
     * 保存文章
     * @param ariticle
     * @return
     */
    @Override
    public ResultData save(Article ariticle) {
        SysUser currSysUser =(SysUser) SecurityUtils.getSubject().getPrincipal();
        ariticle.setDisable(1);
        ariticle.setHits(0);
        ariticle.setAuthorid(currSysUser.getId());
        ariticle.setCreatetime(new Date());
        ariticleMapper.insert(ariticle);
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_ADD_MESSAGE);
    }



    /**
     * 分页获取文章列表
     * @param article
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ArticleVo> searchArticles(Article article, Integer currentPage, Integer pageSize) {
        if(article.getStatus()!= null && article.getType()!= null){
            if(article.getStatus()==0){
                article.setStatus(null);
            }if(article.getType()==0){
                article.setType(null);
            }
        }
        PageHelper.startPage( currentPage,  pageSize);
        List<Article> list= ariticleMapper.select(article);
        List<ArticleVo> listvo=new ArrayList<>();
        for(Article ac:list){
            System.out.println("dasdas");
            ArticleVo articleVo=new ArticleVo();
            articleVo.setAuthor(sysUserMapper.selectByPrimaryKey(ac.getAuthorid()).getUsername());
            articleVo.setStatus(AriticleStatusEnum.getTypeMsg(ac.getStatus()));
            articleVo.setType(AriticleTypeEnum.getTypeMsg(ac.getType()));
            articleVo.setCategories(categoriesMapper.selectByPrimaryKey( ac.getCategories()).getCategoriesName());
           BeanUtils.copyProperties(ac,articleVo);
            listvo.add(articleVo);
        }
        PageInfo<ArticleVo> pageInfo = new PageInfo<>(listvo);
        return pageInfo;
    }

    /**
     * 根据文章ID获取文章列表
     * @param id
     * @return
     */
    @Override
    public ResultData getArticleById(Integer id) {
        Article article = ariticleMapper.selectByPrimaryKey(id);
       //将其的点击量+1
        article.setHits(article.getHits()+1);
        ariticleMapper.updateByPrimaryKeySelective(article);
      //  BeanUtils.copyProperties(article,ariticleVo);
       /* ariticleVo.setAuthor(sysUserMapper.selectByPrimaryKey(article.getAuthorid()).getUsername());
        ariticleVo.setStatus(AriticleStatusEnum.getTypeMsg(article.getStatus()));
        ariticleVo.setType(AriticleTypeEnum.getTypeMsg(article.getType()));
        ariticleVo.setCategories(categoriesMapper.selectByPrimaryKey( article.getCategories()).getCategoriesName());*/
        return ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,article);
    }

    @Override
    public ResultData delete(Integer id) {
        ariticleMapper.deleteByPrimaryKey(id);
        return ResultData.ofSuccess(MsgConst.DEFAULT__SUCCESS_DELETE);
    }
}
