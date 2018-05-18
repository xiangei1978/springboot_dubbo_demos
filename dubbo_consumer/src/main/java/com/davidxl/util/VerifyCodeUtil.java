package com.davidxl.util;

import javax.servlet.http.HttpServletRequest;

public class VerifyCodeUtil {
    private static String verifyCode_attr_name = "verifyCode";

    public static void createVerifyCode(HttpServletRequest httpServletRequest,String verifyCode){

        httpServletRequest.getSession().setAttribute(verifyCode_attr_name, verifyCode);
    }

    public static boolean checkVerifyCode(HttpServletRequest httpServletRequest,String verifyCode){
       String str =  (String)httpServletRequest.getSession().getAttribute(verifyCode_attr_name);

       if (str ==null)
           return false;

       if (str.equals(verifyCode)){
           return true;
       }
       else{
           httpServletRequest.getSession().removeAttribute(verifyCode_attr_name);
           return false;
        }

    }
}
