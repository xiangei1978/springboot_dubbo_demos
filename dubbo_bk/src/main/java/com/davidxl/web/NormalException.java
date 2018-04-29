package com.davidxl.web;

import com.davidxl.common.type.NormalResultType;

/**
 * Created by xianglei on 2018/4/21.
 */
public class NormalException  extends RuntimeException{
    private Integer code;

    public NormalException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public NormalException(NormalResultType errType) {
        super(errType.getMsg());
        this.code = errType.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
