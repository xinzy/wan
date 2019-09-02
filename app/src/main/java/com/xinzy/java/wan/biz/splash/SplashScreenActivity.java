package com.xinzy.java.wan.biz.splash;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseActivity;

import static com.xinzy.java.wan.util.Macro.ROUTER_MAIN;

@Layout(R.layout.activity_splash_screen)
public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(this::gotoMain, 500);
    }

    private void gotoMain() {
        ARouter.getInstance().build(ROUTER_MAIN).navigation(this);
        finish();
    }
}
