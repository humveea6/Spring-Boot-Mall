package com.imooc.mall;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.exception.UserLoginException;
import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-04-17
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("prehandler 233");
        User user = (User)request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null){
            log.info("user==null");
            throw new UserLoginException();
//            return false;
        }

        return true;
    }
}
