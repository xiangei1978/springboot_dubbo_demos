package com.davidxl.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xianglei on 2018/4/21.
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseHttpResult Handle(Exception e){

        if (e instanceof NormalException){
            NormalException normalException = (NormalException) e;
            CommonResult CR = new CommonResult( normalException.getCode(),null,
                                                normalException.getMessage() );

            return new ResponseHttpResult(HttpStatus.OK.value(), CR );

        }else {
            //将系统异常以打印出来
            logger.error("[系统异常]{}",e);
            return new ResponseHttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage() ) ;
        }

    }
}
