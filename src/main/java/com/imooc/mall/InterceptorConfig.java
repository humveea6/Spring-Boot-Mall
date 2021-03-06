package com.imooc.mall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created on 2020-04-17
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new UserLoginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/user/login","/error").
//                excludePathPatterns("user/register","/products","/products/*");

        registry.addInterceptor(new LogRecordInteceptor()).addPathPatterns("/**");
    }
}
