package com.barnettwong.quyou.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.TaskStackBuilder;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.ui.activity.guide.IntroActivity;
import com.blankj.utilcode.util.SPUtils;
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
            boolean isFirst=SPUtils.getInstance().getBoolean(AppConstant.IS_FIRST_ENTER,true);
            if(isFirst){
                TaskStackBuilder.create(SplashActivity.this)
                        .addNextIntentWithParentStack(new Intent(SplashActivity.this, MainActivity.class))
                        .addNextIntent(new Intent(SplashActivity.this, IntroActivity.class))
                        .startActivities();
                finish();
            }else{
                startActivity(MainActivity.class);
                finish();
            }
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
