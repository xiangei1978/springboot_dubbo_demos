package com.davidxl.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianglei on 2018/4/17.
 */
public enum ZKLockPrefix implements BaseEnum<ZKLockPrefix, String> {
    UserLockPrefix("UserLockPrefix","用户锁前缀"),
    OrderLockPrefix("OrderLockPrefix","订单锁前缀");

    private String value;
    private String displayName;


    static Map<String,ZKLockPrefix> enumMap=new HashMap<String, ZKLockPrefix>();
    static{
        for(ZKLockPrefix type: ZKLockPrefix.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private ZKLockPrefix(String value, String displayName) {
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

    public static ZKLockPrefix getEnum(String value) {
        return enumMap.get(value);
    }
}
