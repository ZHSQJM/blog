package com.zhs.controller;

import com.github.pagehelper.PageInfo;
import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.entity.Article;
import com.zhs.entity.Categories;
import com.zhs.oss.IQiniuService;
import com.zhs.service.IArticleService;
import com.zhs.service.ICategoriesService;
import com.zhs.vo.ArticleVo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2019/1/16 09:47
 * @package: com.zhs.controller
 * @description:
 */

@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IQiniuService qiniuService;

    @Autowired
    private ICategoriesService categoriesService;

    @GetMapping(value="list.html")
    @RequiresPermissions("article:list")
    @Log("获取博客列表")
    public String list(Article article, @RequestParam(value="currentPage",defaultValue = "1") int currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10") int pageSize, HttpServletRequest request){
        //只找到已经发布过的博客
       // article.setStatus(1);
        PageInfo<ArticleVo> pageInfo = articleService.searchArticles(article,currentPage, pageSize);
        List<Categories> allCategories = categoriesService.getAllCategories();
        request.setAttribute("categories",allCategories);
        request.setAttribute("article", article);
        request.setAttribute("articleinfo", pageInfo);
        return "blog/article";
    }

    /**
     * 去新增博客页面
     * @return
     */
    @GetMapping(value="add.html")
    public String toAdd(HttpServletRequest request){
        List<Categories> allCategories = categoriesService.getAllCategories();
        request.setAttribute("categories",allCategories);
        return  "blog/add";
    }


    @Log("新增博客")
    @PostMapping("add")
    @ResponseBody
    public ResultData add(@Validated Article article,  MultipartFile uploadFile) throws Exception {
      String url=(String) qiniuService.upload(uploadFile).getData();
        article.setTitleurl(url);
            return   articleService.save(article);
    }
    @ApiOperation(value="删除博客",notes = "删除博客")
    @PostMapping("/delete/{id}")
    @Log("删除博客")
    @ResponseBody
    public ResultData delete(@PathVariable("id") Integer id)  {
        return  articleService.delete(id);
    }

    @ApiOperation(value="去编辑博客的页面",notes = "去编辑博客的页面")
    @GetMapping("/update.html")
    @Log("去编辑博客的页面")
    public String update(HttpServletRequest request)  {
        List<Categories> allCategories = categoriesService.getAllCategories();
        request.setAttribute("categories",allCategories);
        return  "blog/update";
    }
    @ApiOperation(value="根据ID获取博客",notes = "根据ID获取博客")
    @PostMapping("/getArticle/{id}")
    @Log("根据ID获取博客")
    @ResponseBody
    public ResultData getArticleById(@PathVariable("id") Integer id){
        return articleService.getArticleById(id);
    }


}
