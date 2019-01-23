package com.zhs.controller;

import com.zhs.ResultData;
import com.zhs.annotation.Log;
import com.zhs.constants.MsgConst;
import com.zhs.entity.Article;
import com.zhs.entity.Categories;
import com.zhs.service.ICategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.MacSpi;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @project: zhs-blog
 * @author: zhs
 * @date: 2018/12/7 15:24
 * @package: com.zhs.controller
 * @description:
 */
@Api(value = "类目类的增删改查",tags = "博客类目接口")
@Controller
@RequestMapping("/categories")
public class CategoriesController {


    @Autowired
    private ICategoriesService categoriesService;

    @ApiOperation(value="新增类目",notes = "新增类目")
    @PostMapping("/saveCategories")
    @Log("新增类目")
    public ResultData saveCategories(Categories categories){
        return categoriesService.save(categories);
    }


    @ApiOperation(value="查询所有类目",notes = "查询所有类目(部分也)")
    @GetMapping("/list.html")
    @Log("查询所有类目")
    public String list(HttpServletRequest request){

      List<Categories> list = categoriesService.getAllCategories();
        request.setAttribute("list",list);
      return "categories/list";
    }

    @ApiOperation(value="查询所有类目",notes = "查询所有类目(部分也)")
    @GetMapping("/getAllCategories")
    @Log("查询所有类目")
    @ResponseBody
    public ResultData getAllCategories(){

        List<Categories> list = categoriesService.getAllCategories();
        return  ResultData.ofSuccess(MsgConst.DEFAULT_SUCCESS_SELECT_MESSAGE,list);
    }
}
