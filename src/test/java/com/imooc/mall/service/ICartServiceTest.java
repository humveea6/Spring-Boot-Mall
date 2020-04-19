package com.imooc.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
@Slf4j
public class ICartServiceTest extends MallApplicationTests {

    @Autowired
    ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void addCart() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(29);
        cartAddForm.setSelected(true);
        cartService.addCart(1,cartAddForm);
    }

    @Test
    public void listCart(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.listCart(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
}