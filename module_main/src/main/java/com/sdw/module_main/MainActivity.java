package com.sdw.module_main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sdw.module_common.constant.ARouterPath;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        findViewById(R.id.tv_main).setOnClickListener(view -> {
            ARouter.getInstance().build(ARouterPath.MODULELOGIN_LOGINACTIVITY).navigation();
        });
    }
}