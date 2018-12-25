package com.barnettwong.quyou.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.util.MyUtils;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 14:58
 * 精选
 */
public class NewsFragment extends BaseFragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        List<String> channelNames = new ArrayList<>();
        String[] typeNames=getResources().getStringArray(R.array.book_type_names);
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < typeNames.length; i++) {
            channelNames.add(typeNames[i]);
            mNewsFragmentList.add(createListFragments(typeNames[i]));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
//        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private BookTypeFragment createListFragments(String tag) {
        BookTypeFragment fragment = new BookTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.BOOK_TYPE, tag);
        fragment.setArguments(bundle);
        return fragment;
    }
}
