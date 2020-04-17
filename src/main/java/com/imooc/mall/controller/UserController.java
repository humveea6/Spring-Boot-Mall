package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.UserLoginform;
import com.imooc.mall.form.UserRegisterform;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-14
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;

    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterform userRegisterform,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("参数错误,{}",bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterform,user);
        user.setRole(1);
        return iUserService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginform userLoginform,
                                  BindingResult bindingResult, HttpSession httpSession){
        if(bindingResult.hasErrors()){
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getDefaultMessage());
        }

        ResponseVo<User> userResponseVo = iUserService.login(userLoginform.getUsername(), userLoginform.getPassword());

        //session
        httpSession.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());

        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession httpSession){
        User user = (User)httpSession.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession httpSession){
        httpSession.removeAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success("退出成功");
    }

}
