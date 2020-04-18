package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-18
 */
public interface IProductService {

    ResponseVo<PageInfo> getProductList(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVo<ProductDetailVo> getProductDetail(Integer productId);
}
