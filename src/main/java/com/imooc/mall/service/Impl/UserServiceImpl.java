package com.imooc.mall.service.Impl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.User;
import com.imooc.mall.pojo.UserExample;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

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
    public ResponseVo<User> register(User user) {
        //username不重复
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        if (readUserMapper.countByExample(userExample) > 0){
//            throw new RuntimeException("该username已注册");
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        //email不重复
        UserExample userExample1 = new UserExample();
        UserExample.Criteria criteria1 = userExample1.createCriteria();
        criteria1.andEmailEqualTo(user.getEmail());
        if(readUserMapper.countByExample(userExample1) > 0){
//            throw new RuntimeException("该email已被注册");
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //MD5 摘要
        String s = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(s);

        //写入数据库
        int resultCount = writeUserMapper.insertSelective(user);
        if (resultCount == 0){
//            throw  new RuntimeException("注册失败");
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.successByMsg();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> userList = readUserMapper.selectByExample(userExample);
        if(userList == null){
            //return error
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        User user = userList.get(0);
        if(!user.getPassword().equalsIgnoreCase(DigestUtils.
                md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            //password error
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");

        return ResponseVo.success(user);
    }
}
