package com.davidxl.web;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by xianglei on 2018/4/21.
 */
@Data
public class ResponseHttpResult {

    private int status = HttpStatus.OK.value();

    private Object content;

    private String errMsg;

    public ResponseHttpResult(Object content) {
        this.content = content;
    }

    public ResponseHttpResult(int status, Object content) {
        this(content);
        this.status = status;
    }

    public ResponseHttpResult(int status, Object content,String errMsg) {
        this(content);
        this.status = status;
        this.errMsg = errMsg;
    }


//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public Object getContent() {
//        return content;
//    }
//
//    public void setContent(Object content) {
//        this.content = content;
//    }
}
