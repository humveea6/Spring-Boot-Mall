package com.imooc.mall.form;

import javax.validation.constraints.NotNull;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-19
 */
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
