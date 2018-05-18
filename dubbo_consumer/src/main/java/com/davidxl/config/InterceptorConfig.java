package com.davidxl.config;

import com.davidxl.config.properties.WebProperties;
import com.davidxl.service.system.token.EmployeeTokenService;
import com.davidxl.service.system.token.impl.EmployeeTokenServiceImpl;
import com.davidxl.web.interceptor.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private WebProperties webProperties;



    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new   CommonInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration interceptorRegistration =registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
        InterceptorRegistration interceptorRegistration =registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
        if(!CollectionUtils.isEmpty(webProperties.getUriIgnore())){
            interceptorRegistration.excludePathPatterns(webProperties.getUriIgnore().toArray(new String[]{}));
        }
    }
}