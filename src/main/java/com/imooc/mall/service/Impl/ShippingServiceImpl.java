package com.imooc.mall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.ShippingMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.AddressAddForm;
import com.imooc.mall.pojo.Shipping;
import com.imooc.mall.service.IShippingService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-23
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String,Integer>> add(Integer uid,AddressAddForm addressAddForm) {

        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(addressAddForm,shipping);
        int row = shippingMapper.insertSelective(shipping);

        if(row == 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        Map<String,Integer> resMap = new HashMap<>();
        resMap.put("shippingId",shipping.getId());

        return ResponseVo.success(resMap);
    }

    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {
        return null;
    }

    @Override
    public ResponseVo update(Integer uid, Integer shippingId, AddressAddForm addressAddForm) {
        return null;
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNo, Integer pageSize) {
        return null;
    }
}
