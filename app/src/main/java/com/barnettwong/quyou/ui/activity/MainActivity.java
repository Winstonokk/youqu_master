package com.barnettwong.quyou.ui.activity;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.app.MyApplication;
import com.barnettwong.quyou.bean.TabEntity;
import com.barnettwong.quyou.ui.fragment.GirlFragment;
import com.barnettwong.quyou.ui.fragment.MineFragment;
import com.barnettwong.quyou.ui.fragment.MovieFragment;
import com.barnettwong.quyou.ui.fragment.NewsFragment;
import com.barnettwong.quyou.util.UiUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.BindView;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private String[] mTitles = {"图书", "美女","视频","我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_jingxuan_nor,R.mipmap.ic_home_girl_nor,R.mipmap.ic_home_video_nor,R.mipmap.ic_home_mine_nor};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_jingxuan_sel,R.mipmap.ic_home_girl_sel, R.mipmap.ic_home_video_sel,R.mipmap.ic_home_mine_sel};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private NewsFragment mNewsFragment;
    private GirlFragment mGirlFragment;
    private MovieFragment mMovieFragment;
    private MineFragment mMineFragment;
    private static int tabLayoutHeight;

    private VideoViewManager mVideoViewManager;
    //是否退出
    private static boolean isExit;

    //处理退出发出的消息
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        SPUtils.getInstance().put(AppConstant.IS_FIRST_ENTER,false);
        mVideoViewManager = VideoViewManager.instance();
        //初始化菜单
        initTab();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initPermissions();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //切换daynight模式要立即变色的页面
        ChangeModeController.getInstance().init(this,R.attr.class);
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
        tabLayout.measure(0,0);
        tabLayoutHeight=tabLayout.getMeasuredHeight();

    }
    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mVideoViewManager.stopPlayback();//切换时直接停止播放
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    private void initPermissions() {
        //同时请求多个权限
        RxPermissions.getInstance(MainActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.SET_DEBUG_APP,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.WRITE_APN_SETTINGS,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)//多个权限用","隔开
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            //只要有一个权限禁止，返回false，
                            //下一次申请只申请没通过申请的权限
                            finish();
                        }
                    }
                });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            mNewsFragment = (NewsFragment) getSupportFragmentManager().findFragmentByTag("mNewsFragment");
            mGirlFragment = (GirlFragment) getSupportFragmentManager().findFragmentByTag("mGirlFragment");
            mMovieFragment = (MovieFragment) getSupportFragmentManager().findFragmentByTag("mMovieFragment");
            mMineFragment = (MineFragment) getSupportFragmentManager().findFragmentByTag("mMineFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            mNewsFragment = new NewsFragment();
            mGirlFragment = new GirlFragment();
            mMovieFragment = new MovieFragment();
            mMineFragment = new MineFragment();

            transaction.add(R.id.fl_body, mNewsFragment, "mNewsFragment");
            transaction.add(R.id.fl_body, mGirlFragment, "mGirlFragment");
            transaction.add(R.id.fl_body, mMovieFragment, "mMovieFragment");
            transaction.add(R.id.fl_body, mMineFragment, "mMineFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        mVideoViewManager.stopPlayback();//切换时直接停止播放
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(mMineFragment);
                transaction.hide(mGirlFragment);
                transaction.hide(mMovieFragment);
                transaction.show(mNewsFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(mNewsFragment);
                transaction.hide(mMineFragment);
                transaction.hide(mMovieFragment);
                transaction.show(mGirlFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(mNewsFragment);
                transaction.hide(mGirlFragment);
                transaction.hide(mMineFragment);
                transaction.show(mMovieFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 3:
                transaction.hide(mNewsFragment);
                transaction.hide(mGirlFragment);
                transaction.hide(mMovieFragment);
                transaction.show(mMineFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mVideoViewManager.releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (!mVideoViewManager.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //如果isExit标记为false，提示用户再次按键
            if (!isExit) {
                isExit = true;
                UiUtils.makeText(this, getString(R.string.str_exitApp));
                //如果用户没有在3秒内再次按返回键的话，就发送消息标记用户为不退出状态
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
            //如果isExit标记为true，退出程序
            else {
                //退出程序
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.loge("onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    //友盟分享  start
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题(此处在fragment进行，暂不考虑)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mShareAction.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        ChangeModeController.onDestory();
    }

    //友盟分享 end
}
