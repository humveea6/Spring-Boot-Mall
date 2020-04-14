package com.imooc.mall.service;

import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-14
 */
public interface IUserService {
    /*
    注册
     */
    ResponseVo register(User user);
    /*
    登录
     */
}
