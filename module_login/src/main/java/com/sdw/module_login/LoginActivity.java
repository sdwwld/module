package com.sdw.module_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sdw.module_common.constant.ARouterPath;

@Route(path = ARouterPath.MODULELOGIN_LOGINACTIVITY)
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        findViewById(R.id.tv_login).setOnClickListener(view -> {
            ARouter.getInstance().build(ARouterPath.MODULEMINE_MINEACTIVITY)
                    .withString("param", "我要到个人中心页面").navigation();
        });
    }
}
