package com.imooc.mall.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.Userform;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-14
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody Userform userform,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("参数错误,{}",bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userform,user);
        user.setRole(1);
        return iUserService.register(user);
    }

}
