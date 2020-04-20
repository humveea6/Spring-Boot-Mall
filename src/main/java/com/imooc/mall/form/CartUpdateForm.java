package com.imooc.mall.form;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-20
 */
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
