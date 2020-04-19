package com.imooc.mall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public Boolean getSelectedAll() {
        return selectedAll;
    }

    public void setSelectedAll(Boolean selectedAll) {
        this.selectedAll = selectedAll;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Integer getCartTotalQuantity() {
        return cartTotalQuantity;
    }

    public void setCartTotalQuantity(Integer cartTotalQuantity) {
        this.cartTotalQuantity = cartTotalQuantity;
    }

    @Override
    public String toString() {
        return "CartVo{" +
                "cartProductVoList=" + cartProductVoList +
                ", selectedAll=" + selectedAll +
                ", cartTotalPrice=" + cartTotalPrice +
                ", cartTotalQuantity=" + cartTotalQuantity +
                '}';
    }
}
