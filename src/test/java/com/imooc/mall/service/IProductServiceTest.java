package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 2020-04-18
 */
public class IProductServiceTest extends MallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void getProductList() {
        ResponseVo<PageInfo> productList = productService.getProductList(null, 2, 2);
//        System.out.println(productList.getStatus());
//        for(ProductVo productVo:productList.getData()){
//            System.out.println(productVo);
//        }
    }

    @Test
    public void getProductDetailTest(){
        ResponseVo<ProductDetailVo> productDetailVoResponseVo = productService.getProductDetail(26);
    }
}