package com.imooc.mall.form;

import javax.validation.constraints.NotNull;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-05
 */
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }
}