package com.davidxl.common.status;

import com.davidxl.common.BaseEnum;

import java.util.HashMap;
import java.util.Map;



public enum EmployeeStatus implements BaseEnum<TokenStatus, String> {
    NORMAL("NORMAL","正常"),
    QUIT("QUIT","离职"),
    SUSPEND("SUSPEND","账户暂停"),
    FORBID("FORBID","禁止登陆");

    private String value;
    private String displayName;


    static Map<String,TokenStatus> enumMap=new HashMap<String, TokenStatus>();
    static{
        for(TokenStatus type: TokenStatus.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private EmployeeStatus(String value, String displayName) {
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