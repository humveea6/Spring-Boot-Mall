package com.imooc.mall.service;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
public interface ICartService {

    ResponseVo<CartVo> addCart(Integer uid,CartAddForm cartAddForm);

    ResponseVo<CartVo> listCart(Integer uid);

    ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    ResponseVo<CartVo> deleteCart(Integer uid,Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);
}
