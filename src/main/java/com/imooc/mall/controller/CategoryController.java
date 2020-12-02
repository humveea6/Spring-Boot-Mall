package com.imooc.mall.controller;

import com.imooc.mall.pojo.Category;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.utils.HlgGsonUtil;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-18
 */
@RestController
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAllCategories(){
        return categoryService.selectAllCategory();
    }

    @GetMapping("/testsql")
    public ResponseVo test(){
//        List<Integer> ids = Arrays.asList(100001, 100002, 100003);
//        List<Category> category = categoryService.getCategory(ids);
        Category oneCategory = categoryService.getOneCategory(100001);
        return ResponseVo.success(oneCategory);
    }

}
