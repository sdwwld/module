package com.sdw.module_mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sdw.module_common.constant.ARouterPath;

@Route(path = ARouterPath.MODULEMINE_MINEACTIVITY)
public class MineActivity extends AppCompatActivity {
    @Autowired(name = "param")
    public String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_mine);
        ARouter.getInstance().inject(this);
//        Toast.makeText(getApplicationContext(), param, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), getIntent().getExtras().getString("param"), Toast.LENGTH_LONG).show();
    }
}
