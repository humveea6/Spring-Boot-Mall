package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.form.AddressAddForm;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created on 2020-04-23
 */
public interface IShippingService {

    ResponseVo<Map<String,Integer>> add(Integer uid, AddressAddForm addressAddForm);

    ResponseVo delete(Integer uid,Integer shippingId);

    ResponseVo update(Integer uid,Integer shippingId,AddressAddForm addressAddForm);

    ResponseVo<PageInfo> list(Integer uid,Integer pageNo,Integer pageSize);
}
