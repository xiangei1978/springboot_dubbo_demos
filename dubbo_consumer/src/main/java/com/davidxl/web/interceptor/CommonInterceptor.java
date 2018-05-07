package com.davidxl.web.interceptor;


import com.davidxl.common.CheckAuthority;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler："+handler.getClass());
        // 将handler强转为HandlerMethod, 前面已经证实这个handler就是HandlerMethod
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 从方法处理器中获取出要调用的方法
        Method method = handlerMethod.getMethod();
        // 获取出方法上的CheckAuthority注解
        CheckAuthority access = method.getAnnotation(CheckAuthority.class);
        if (access == null) {
            // 如果注解为null, 说明不需要拦截, 直接放过
            return true;
        }
        if (access.funcs().length > 0) {
            // 如果权限配置不为空, 则取出配置值
            String[] authorities = access.funcs();
            Set<String> authSet = new HashSet<>();
            for (String authority : authorities) {
                // 将权限加入一个set集合中
                authSet.add(authority);
            }

            // 到数据库权限表中查询用户拥有的权限集合, 与set集合中的权限进行对比完成权限校验
            if (authSet.contains("sys.test.pass")) {
                // 校验通过返回true, 否则拦截请求
                return true;
            }


//            String role = request.getParameter("role");
//            if (StringUtils.isNotBlank(role)) {
//                if (authSet.contains(role)) {
//                    // 校验通过返回true, 否则拦截请求
//                    return true;
//                }
//            }
        }
        // 拦截之后应该返回公共结果, 这里没做处理
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler"+handler.getClass());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion"+handler.getClass());
    }
}