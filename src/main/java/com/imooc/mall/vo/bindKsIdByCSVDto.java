package com.imooc.mall.vo;

import com.opencsv.bean.CsvBindByName;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-07-06
 */
public class bindKsIdByCSVDto {

    @CsvBindByName
    private Long ksID;

    public Long getKsID() {
        return ksID;
    }

    public void setKsID(Long ksID) {
        this.ksID = ksID;
    }
}
