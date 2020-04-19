package com.imooc.mall.service;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.form.CartAddForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
public class ICartServiceTest extends MallApplicationTests {

    @Autowired
    ICartService cartService;

    @Test
    public void addCart() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(27);
        cartAddForm.setSelected(true);
        cartService.addCart(1,cartAddForm);
    }
}