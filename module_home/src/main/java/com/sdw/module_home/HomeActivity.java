package com.sdw.module_home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sdw.module_common.constant.ARouterPath;

@Route(path = ARouterPath.MODULEHOME_HOMEACTIVITY)
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_home);
    }
}
