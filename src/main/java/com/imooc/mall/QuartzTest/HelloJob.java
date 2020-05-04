package com.imooc.mall.QuartzTest;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-05-04
 */
@Component
@Slf4j
public class HelloJob {

    @Autowired
    private IProductService productService;

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void cronJob() {
//        Date date = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("Current extime is :"+sf.format(date));
//
//        System.out.println("Hello world!");
//
//        ResponseVo<PageInfo> productList = productService.getProductList(null, 1, 20);
//        Gson gson = new Gson();
//        String toJson = gson.toJson(productList);
//        log.info("com.imooc.mall.QuartzTest :" + toJson);
//    }
}
