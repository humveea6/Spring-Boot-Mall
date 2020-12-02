package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created on 2020-04-19
 */
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.listCart(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.addCart(user.getId(),cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> put(@PathVariable Integer productId,
                                  @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                  HttpSession session,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.updateCart(user.getId(),productId,cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                      HttpSession session){

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.deleteCart(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session){

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session){

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session){

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);

        return cartService.sum(user.getId());
    }
}
