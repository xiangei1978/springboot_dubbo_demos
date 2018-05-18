package com.davidxl.common.status;

import com.davidxl.common.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianglei on 2018/4/17.
 */
public enum TokenStatus implements BaseEnum<TokenStatus, String> {
    ok("ok","token验证通过"),
    err("check_err","token验证未通过"),
    timeout("timeout","token超时"),
    beOverided("beOverrided","被覆盖");

    private String value;
    private String displayName;


    static Map<String,TokenStatus> enumMap=new HashMap<String, TokenStatus>();
    static{
        for(TokenStatus type: TokenStatus.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private TokenStatus(String value, String displayName) {
        this.value=value;
        this.displayName=displayName;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public static TokenStatus getEnum(String value) {
        return enumMap.get(value);
    }
}
