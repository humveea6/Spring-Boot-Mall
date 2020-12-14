package com.imooc.mall.vo;

import com.opencsv.bean.CsvBindByName;


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
