package com.davidxl.web;

import org.springframework.http.HttpStatus;

/**
 * Created by xianglei on 2018/4/21.
 */
public class ResponseHttpResult {

    private int status = HttpStatus.OK.value();

    private Object content;

    public ResponseHttpResult(Object content) {
        this.content = content;
    }

    public ResponseHttpResult(int status, Object content) {
        this(content);
        this.status = status;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
