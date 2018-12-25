package com.barnettwong.quyou.ui.activity;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.blankj.utilcode.util.SPUtils;
import com.jaydenxiao.common.base.BaseActivity;

public class MainActivity extends BaseActivity {


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
    }
}
