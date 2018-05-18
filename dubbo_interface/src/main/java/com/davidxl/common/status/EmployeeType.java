package com.davidxl.common.status;

import com.davidxl.common.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianglei on 2018/4/17.
 */
public enum EmployeeType implements BaseEnum<EmployeeType, String> {
    isAdmin("isAdmin","管理员"),
    isUser("isUser","普通用户");

    private String value;
    private String displayName;


    static Map<String,EmployeeType> enumMap=new HashMap<String, EmployeeType>();
    static{
        for(EmployeeType type: EmployeeType.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private EmployeeType(String value, String displayName) {
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

    public static EmployeeType getEnum(String value) {
        return enumMap.get(value);
    }
}
