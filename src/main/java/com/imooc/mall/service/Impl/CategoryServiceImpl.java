package com.imooc.mall.service.Impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.mall.dao.CategoryMapper;
import com.imooc.mall.pojo.Category;
import com.imooc.mall.pojo.CategoryExample;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-18
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAllCategory() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andStatusEqualTo(true);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);

        for(Category category:categories){
            if(category.getParentId().equals(0)){
                CategoryVo categoryVo = categoryToCategoryVo(category);
                categoryVoList.add(categoryVo);
            }
        }
        findSubCategory(categoryVoList,categories);

        return ResponseVo.success(categoryVoList);
    }

    private void findSubCategory(List<CategoryVo> categoryVoList,List<Category> categories){
        for(CategoryVo categoryVo:categoryVoList){
            List<CategoryVo> subCategoryVoList = new ArrayList<>();

            for(Category category:categories){
                if(categoryVo.getId().equals(category.getParentId())){
                    CategoryVo SubcategoryVo = categoryToCategoryVo(category);
                    subCategoryVoList.add(SubcategoryVo);
                }
            }

            subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategories(subCategoryVoList);

            findSubCategory(subCategoryVoList,categories);
        }
    }

    private CategoryVo categoryToCategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        criteria.andStatusEqualTo(true);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);

        findSubCategoryId(id,resultSet,categories);
    }

    private void findSubCategoryId(Integer id, Set<Integer> resultSet,List<Category> categories){
        for(Category category:categories){
            if(category.getParentId().equals(id)){
                resultSet.add(category.getId());

                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }
}
