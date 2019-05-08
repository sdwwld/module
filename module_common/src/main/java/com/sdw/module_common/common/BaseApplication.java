package com.sdw.module_common.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.sdw.module_common.util.AppTool;


public class BaseApplication extends Application {

    private static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppTool.init(this);
    }

    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
