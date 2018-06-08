package com.davidxl.web.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.davidxl.common.CheckAuthority;
import com.davidxl.common.status.TokenStatus;
import com.davidxl.model.sys.EmployeeTokenCache;
import com.davidxl.service.system.token.EmployeeTokenService;
import com.davidxl.web.ResponseHttpResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    EmployeeTokenService employeeTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandler："+handler.getClass());
//        if (true)
//        return true;



        TokenStatus tokenStatus =  validateTokenByUserAgent( request,  response);

        if (!tokenStatus.equals(TokenStatus.ok)){
            return false;
        }


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


    private  void checkToken(HttpServletRequest request,Object handler){


    }


    private TokenStatus validateTokenByUserAgent(HttpServletRequest httpServletRequest, HttpServletResponse response  ) throws IllegalAccessException,Exception {
        //其他请求获取头信息
        final String authHeader = httpServletRequest.getHeader("token");
        try {
            //如果没有header信息
            if (authHeader == null || authHeader.trim() == "") {
                throw new SignatureException("获取客户端token异常");
            }

            //获取jwt实体对象接口实例
            final Claims claims = Jwts.parser().setSigningKey("davidxl")
                    .parseClaimsJws(authHeader).getBody();
            //从数据库中获取token
            EmployeeTokenCache employeeTokenCache = employeeTokenService.getEmployeeFromCache(new Integer(claims.getSubject()));

            if (employeeTokenCache==null){
                throw new SignatureException("token已过期");
//                return TokenStatus.timeout;
            }
            else{
                if(!employeeTokenCache.getToken().equals(authHeader))
                {
                    log.warn("Header_token = " + authHeader + "\n cache_token = " );
                    //在其他地方被注销
                    throw new SignatureException("用户[" + employeeTokenCache.getEmployeeName() + "]重复登录：ip = " +employeeTokenCache.getLogin_ip() );

                }else
                {
                    httpServletRequest.setAttribute("employeeID",employeeTokenCache.getEmployeeID());
                    return TokenStatus.ok;
                }
            }
        }
        //验证异常处理
        catch (SignatureException | ExpiredJwtException e)
        {

            writeResponseWhenError(e.getMessage(),response);
            return TokenStatus.beOverided;
        }
        //出现异常时
        catch (final Exception e)
        {
            writeResponseWhenError(e.getMessage(),response);
            return TokenStatus.err;
        }

    }
    private void writeResponseWhenError(String errMsg ,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        ResponseHttpResult baseResponse = new ResponseHttpResult(500,null,errMsg);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(baseResponse));
        } catch (IOException e) {
            // log.error("进行JsonResponse输出时发生错误, baseRequest:{}, baseResponse:{}", baseRequest, baseResponse, e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }



}