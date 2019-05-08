package com.sdw.module_common.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sdw.module_common.BuildConfig;

import java.io.File;

/**
 * email：wld314159@163.com
 * description：
 */
public final class AppTool {
    private static Context mContext;

    private AppTool() {
        throw new AssertionError();
    }

    public static void init(Application mApplication) {//必须在application中初始化
        AppTool.mContext = mApplication.getApplicationContext();
//        initLeakCanary(mApplication);
//        initLogger();
//        initStetho(mApplication);
        initARouter(mApplication);
    }

    private static void initARouter(Application mApplication) {
        if (isAppDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(mApplication); // As early as possible, it is recommended to initialize in the Application
    }

    public static boolean isAppDebug() {
        return BuildConfig.DEBUG;
    }

    public static Context getContext() {
        if (mContext == null)
            throw new IllegalArgumentException("the method init(Context mContext) of AppTool must init in Application");
        return mContext;
    }

    public static void install(File apkfile) {
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileprovider", apkfile);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            getContext().startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(install);
        }
    }
}
