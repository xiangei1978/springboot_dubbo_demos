package com.davidxl;

import com.davidxl.util.DateUtil;

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

        System.out.println( "jar can not bu exe!!" );
    }
}
