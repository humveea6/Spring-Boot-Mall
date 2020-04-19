package com.imooc.mall.service;

import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
public interface ICartService {

    ResponseVo<CartVo> addCart(Integer uid,CartAddForm cartAddForm);

    ResponseVo<CartVo> listCart(Integer uid);
}
