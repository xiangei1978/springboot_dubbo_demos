package com.davidxl;

import com.davidxl.util.DateUtil;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.printf(DateUtil.format(new Date(),DateUtil.ShortCompactDateFormat));

        Integer o = 1;
        Assert.isTrue(o==1,"返回值必须唯1");


        System.out.println( "jar can not bu exe!!" );
    }
}
