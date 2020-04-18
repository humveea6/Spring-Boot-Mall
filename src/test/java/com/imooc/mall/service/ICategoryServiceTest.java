package com.imooc.mall.service;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.service.Impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-18
 */
@Slf4j
public class ICategoryServiceTest extends MallApplicationTests {

    @Autowired
    ICategoryService categoryService;

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001,set);
        log.info("set={}",set);
    }
}