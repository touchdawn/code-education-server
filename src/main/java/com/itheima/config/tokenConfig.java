package com.itheima.config;

import com.itheima.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class tokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/users/login","/users/register","/sendEmail")
                .excludePathPatterns("/data","/test")
//                .excludePathPatterns("/lesson/getCourseInfo?courseId=3")
//                .excludePathPatterns("/files/addNewFile","/files/getToken")
        ;

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
