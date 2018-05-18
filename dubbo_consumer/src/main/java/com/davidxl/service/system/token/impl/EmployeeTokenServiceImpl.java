package com.davidxl.service.system.token.impl;

import com.alibaba.fastjson.JSONObject;
import com.davidxl.common.status.TokenStatus;
import com.davidxl.config.properties.MyRedisProperties;
import com.davidxl.model.SysEmployee;
import com.davidxl.model.sys.EmployeeTokenCache;
import com.davidxl.service.system.token.EmployeeTokenService;
import com.davidxl.util.NetworkUtil;
import com.davidxl.util.RedisUtil;

import com.davidxl.util.TokenUtil;
import com.davidxl.web.ResponseHttpResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class EmployeeTokenServiceImpl  implements EmployeeTokenService {


    @Autowired
    private RedisUtil redisDao;

    @Autowired
    private MyRedisProperties myRedisProperties;



    @Cacheable(value = "employeeID_token-cache", key =  "'employeeID_'+#employeeId")
    public EmployeeTokenCache getEmployeeFromCache(Integer employeeId)  {
        return  null;
    }


    /**
     * 将 employee 放入缓存
     * @param employeeCache
     * @return
     */
    @Cacheable(value = "employeeID_token-cache", key =  "'employeeID_'+#employeeCache.employeeID")
    public EmployeeTokenCache putEmployeeInCache(EmployeeTokenCache employeeCache, Long time) {
        //redisDao.addObject("employee-cache-24hrs:" + employeeCache.getEmployeeId(), employeeCache, time, EmployeeCache.class);
        return employeeCache;
    }

    @Cacheable(value = "employeeID_token-cache", key =  "'employeeID_'+#sysEmployee.employeeId")
    public  EmployeeTokenCache createEmployeeNewToken(HttpServletRequest httpServletRequest, SysEmployee sysEmployee)
    {
        EmployeeTokenCache eCache = new EmployeeTokenCache();
        String token =  TokenUtil.createUserNewToken("davidxl", sysEmployee.getEmployeeId().toString(),7200);
        eCache.setCreateTime(System.currentTimeMillis() );
        eCache.setEmployeeID(sysEmployee.getEmployeeId());
        eCache.setEmployeeName(sysEmployee.getName());
        eCache.setToken(token);
        eCache.setUserAgent(httpServletRequest.getHeader("User-Agent")  );
        String ip="";
        try {
            ip= NetworkUtil.getIpAddress(httpServletRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        eCache.setLogin_ip(ip);
        Set roles = new HashSet();
        roles.add("admin");
        eCache.setRoles(roles);

        Set fun_points = new HashSet();
        fun_points.add("sys.test.pass");
        eCache.setFunction_Points(fun_points);

        //time秒
        //redisDao.addObject(myRedisProperties.getRedisPrefix()+":employeeID_token-cache:employeeID_" + sysEmployee.getEmployeeId(), eCache, 3600L, EmployeeTokenCache.class);
        return eCache;
    }


    /**
     * 令牌包含 userId, createTime, expiryTime。
     * User Agent 是加密 token 的密钥，digest 采用 SHA-512 算法生成
     * @return 新的 tokenEntity
     */
//    public TokenEntity createToken(String userAgent, Integer userId) {
//        //初始化 tokenEntity
//        long expire;
//        expire = redisExpireProperties.getExpireMap().get("user-cache-24hrs") * 1000;
//        TokenEntity newTokenEntity = new TokenEntity(userId, expire);
//        newTokenEntity.setCreateTime(System.currentTimeMillis());
//        log.debug("after setting create time:{}", newTokenEntity.getCreateTime());
//        //生成 token
//        String jsonTokenWithoutUserAgent = JSON.toJSONString(newTokenEntity);
//        log.debug("jsonTokenWithoutUserAgent:{}", jsonTokenWithoutUserAgent);
//        String encryptedTxt = null;
//        try {
//            encryptedTxt = CryptoUtil.aesEncryptToBase64(jsonTokenWithoutUserAgent, userAgent);
//        } catch (CipherException e) {
//            log.warn("jsonTokenWithoutUserAgent:{}, userAgent:{}", jsonTokenWithoutUserAgent, userAgent, e);
//        }
//        newTokenEntity.setToken(encryptedTxt);
//        log.debug("after generating token:{}", newTokenEntity.getToken());
//        //生成 digest
//        try {
//            String sha512 = DigestUtil.sha512ToHex(String.valueOf(newTokenEntity.getUserId() +
//                    newTokenEntity.getCreateTime()));
//            newTokenEntity.setDigest(sha512);
//        } catch (CipherException ce) {
//            log.warn("userId:{}", newTokenEntity.getUserId(), ce.toString()); //ignore
//        }
//        log.debug("after generating digest:{}", newTokenEntity.getDigest());
//        //填入 userAgent
//        newTokenEntity.setUserAgent(userAgent);
//        return newTokenEntity;
//    }

    @CacheEvict(value="employeeID_token-cache", key =  "'employeeID_'+#employeeId")
    public void clearEmployeeFromCache(Integer employeeId) {
        log.debug("从redis删除用户登录信息，employeeId:{}", employeeId);
        //redisDao.delete(myRedisProperties.getRedisPrefix()+":employeeID_token-cache:employeeID_" + employeeId);
    }


}
