package com.sdw.module_common.common;

import com.sdw.module_common.util.GJsonUtil;

import java.io.Serializable;


public class BaseModel implements Serializable {

    @Override
    public String toString() {
        return GJsonUtil.toJson(this);
    }
}
