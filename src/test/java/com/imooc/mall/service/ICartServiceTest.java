package com.imooc.mall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
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
        cartAddForm.setProductId(26);
        cartAddForm.setSelected(true);
        cartService.addCart(1,cartAddForm);
    }

    @Test
    public void listCart(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.listCart(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }

    @Test
    public void updateCart(){
        CartUpdateForm cartUpdateForm = new CartUpdateForm();
        cartUpdateForm.setQuantity(5);
        cartUpdateForm.setSelected(true);
        ResponseVo<CartVo> cartVoResponseVo = cartService.updateCart(1, 26, cartUpdateForm);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }

    @Test
    public void deleteCart(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.deleteCart(1, 26);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }

    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.unSelectAll(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }

    @Test
    public void sum(){
        ResponseVo<Integer> sum = cartService.sum(1);
        log.info("list={}",gson.toJson(sum));
    }
}