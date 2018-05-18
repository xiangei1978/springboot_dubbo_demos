package com.davidxl.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class TokenUtil {



    /**
     * 创建新token
     * @param userID
     * @return
     */
    public static   String createUserNewToken(String keyofsign,String userID,Integer expirationSeconds){
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + expirationSeconds*1000);
        return Jwts
                .builder()
                .setSubject(userID)//对应的用户
                //.claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now)//签发时间
                .setIssuer("com.davidxl")//签发者
                .setExpiration(expiration)//过期时间
                .signWith(SignatureAlgorithm.HS256, keyofsign)//签名
                .compact();
    }

}
