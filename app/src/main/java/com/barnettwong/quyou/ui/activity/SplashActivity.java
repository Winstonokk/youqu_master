package com.barnettwong.quyou.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.preference.Preference;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.jaydenxiao.common.base.BaseActivity;

import site.gemus.openingstartanimation.LineDrawStrategy;
import site.gemus.openingstartanimation.OpeningStartAnimation;

/**
 * Created by wang on 2018/12/24 17:57
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(MainActivity.class);//暂时不做引导页
            finish();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        handler.sendEmptyMessageDelayed(0, 2000);
        OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation.Builder(this)
                .setDrawStategy(new LineDrawStrategy())
                .setAppStatement(getString(R.string.app_statement))
                .setAnimationFinishTime(2000)
                .create();
        openingStartAnimation.show(this);
    }


}
