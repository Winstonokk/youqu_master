package com.barnettwong.quyou.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.TabEntity;
import com.barnettwong.quyou.ui.fragment.GirlFragment;
import com.barnettwong.quyou.ui.fragment.MineFragment;
import com.barnettwong.quyou.ui.fragment.MovieFragment;
import com.barnettwong.quyou.ui.fragment.NewsFragment;
import com.blankj.utilcode.util.SPUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;

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
        //初始化菜单
        initTab();
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
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
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

    /**
     * 菜单显示隐藏动画
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide){
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if(!showOrHide){
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        }else{
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height= (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator,alpha);
        animatorSet.start();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChangeModeController.onDestory();
    }
}
