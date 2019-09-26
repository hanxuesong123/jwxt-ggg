package com.jwxt;

import com.jwxt.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
public class TeacherConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override //添加自定义拦截器到springboot拦截器池中
    protected  void addInterceptors(InterceptorRegistry registry){
        //registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/teacher/login");
        //super.addInterceptors(registry);
    }
}
