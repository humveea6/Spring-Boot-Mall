package com.imooc.mall.service.Impl;

import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created on 2020-04-14
 */
public class UserServiceImplTest extends MallApplicationTests {
    @Autowired
    private IUserService iUserService;

    @Test
    public void register() {
        User user = new User("Jack","123456","",1);

        iUserService.register(user);
    }
}