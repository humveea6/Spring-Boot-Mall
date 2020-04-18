package com.imooc.mall.service;

import com.imooc.mall.vo.CategoryVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-18
 */
public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAllCategory();

}
