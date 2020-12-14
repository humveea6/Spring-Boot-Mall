package com.imooc.mall.service;

import com.imooc.mall.pojo.Category;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * Created on 2020-04-18
 */
public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAllCategory();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);

    List<Category> getCategory(List<Integer> ids);

    Category getOneCategory(Integer id);
}
