package com.imooc.mall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.pojo.ProductExample;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 2020-04-18
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<PageInfo> getProductList(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();

        if(categoryId != null) {
            iCategoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        List<Integer> categoryIdList = new ArrayList<>(categoryIdSet);
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andStatusEqualTo(1);
        if(!categoryIdSet.isEmpty()) {
            criteria.andCategoryIdIn(categoryIdList);
        }

        PageHelper.startPage(pageNum,pageSize);

        List<Product> productList = productMapper.selectByExample(productExample);
        List<ProductVo> productVoList = new ArrayList<>();
        for(Product product:productList){
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product,productVo);
            productVoList.add(productVo);
        }

        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductDetailVo> getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);

        if(product.getStatus().equals(1)) {
            ProductDetailVo productDetailVo = new ProductDetailVo();
            BeanUtils.copyProperties(product, productDetailVo);

            return ResponseVo.success(productDetailVo);
        }
        else {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
    }
}
