package com.davidxl.common;

/**
 * Created by xianglei on 2018/4/17.
 */

public interface BaseEnum<E extends Enum<?>, T> {
    public T getValue();
    public String getDisplayName();
}