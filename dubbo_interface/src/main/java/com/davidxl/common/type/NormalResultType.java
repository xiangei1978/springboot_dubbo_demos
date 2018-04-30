package com.davidxl.common.type;

/**
 * Created by xianglei on 2018/4/21.
 */
public enum NormalResultType {
    ok(0,"正常"),
    err_database_insert(-3000,"数据库insert操作异常"),
    err_database_update(-3001,"数据库update操作异常"),
    err_unknown(-9999,"未知异常");



    private Integer code;

    private String msg;

    NormalResultType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
