package com.imooc.mall.service.Impl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.pojo.User;
import com.imooc.mall.pojo.UserExample;
import com.imooc.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-14
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper readUserMapper;

    @Autowired
    UserMapper writeUserMapper;

    @Override
    public void register(User user) {
        //username不重复
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        if (readUserMapper.countByExample(userExample) > 0){
            throw new RuntimeException("该username已注册");
        }

        //email不重复
        UserExample userExample1 = new UserExample();
        UserExample.Criteria criteria1 = userExample1.createCriteria();
        criteria1.andEmailEqualTo(user.getEmail());
        if(readUserMapper.countByExample(userExample1) > 0){
            throw new RuntimeException("该email已被注册");
        }

        //MD5 摘要
        String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(s);

        //写入数据库
        int resultCount = writeUserMapper.insertSelective(user);
        if (resultCount == 0){
            throw  new RuntimeException("注册失败");
        }

    }
}
