package com.davidxl.web;

import com.davidxl.common.type.NormalResultType;

/**
 * Created by xianglei on 2018/4/21.
 */
public class CommonResult {
    private Integer code = NormalResultType.ok.getCode();
    private String errMsg;
    private Object data;

    public CommonResult(Integer code,Object data,String errMsg) {

        this.code = code;
        this.data = data;
        this.errMsg = errMsg;
    }
    public CommonResult() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
