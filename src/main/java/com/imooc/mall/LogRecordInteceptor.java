package com.imooc.mall;

import com.imooc.mall.annotation.LogRecord;
import com.imooc.mall.dao.LogRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-08-14
 */
public class LogRecordInteceptor implements HandlerInterceptor {

    @Autowired
    private LogRecordMapper logRecordMapper;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("enter log interceptor 233");
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            System.out.println("enter log interceptor hnadler 233");
            LogRecord record = findAnnotation((HandlerMethod) handler, LogRecord.class);
            if(record != null){
                System.out.println("enter log interceptor 233 reocrd not null");
                com.imooc.mall.pojo.LogRecord logRecord = new com.imooc.mall.pojo.LogRecord();
                logRecord.setOperator(record.operator());
                logRecord.setOperatetime(System.currentTimeMillis());
                logRecord.setPassword(record.pwd());
                int i = logRecordMapper.insertSelective(logRecord);
            }
            else{
                System.out.println("fail 233333");
            }
        }
        return true;
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }
}
