package com.barnettwong.quyou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.ui.activity.AboutUsActivity;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wang on 2018/12/25 14:58
 * 个人中心
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.ll_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.switch_btn)
    SwitchButton switchBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        MyOnClickListener onClickListener=new MyOnClickListener();
        llShare.setOnClickListener(onClickListener);
        llAboutUs.setOnClickListener(onClickListener);
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ChangeModeController.toggleThemeSetting(getActivity());
            }
        });
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_share:
//                    ShareBottomDialog dialog = new ShareBottomDialog();
//                    dialog.show(getFragmentManager());
                    break;
                case R.id.ll_about_us:
                    startActivity(AboutUsActivity.class);
                    break;
            }
        }
    }


}
