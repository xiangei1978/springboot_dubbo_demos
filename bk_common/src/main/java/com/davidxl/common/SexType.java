package com.davidxl.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xianglei on 2018/4/17.
 */
public enum SexType implements BaseEnum<SexType, String> {
    male("male","男性"),
    female("female","女性"),
    unknown("unknown","未知");

    private String value;
    private String displayName;


    static Map<String,SexType> enumMap=new HashMap<String, SexType>();
    static{
        for(SexType type: SexType.values()){
            enumMap.put(type.getValue(), type);
        }
    }

    private SexType(String value,String displayName) {
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

    public static SexType getEnum(String value) {
        return enumMap.get(value);
    }
}
