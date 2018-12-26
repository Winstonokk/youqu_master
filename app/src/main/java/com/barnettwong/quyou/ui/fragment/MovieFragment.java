package com.barnettwong.quyou.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.video.VideoChannelTable;
import com.barnettwong.quyou.bean.video.VideosChannelTableManager;
import com.barnettwong.quyou.util.MyUtils;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 14:58
 * 视频主fragment
 */
public class MovieFragment extends BaseFragment{
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;

    private VideoViewManager mVideoViewManager;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_movie;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    protected void initView() {
        mVideoViewManager = VideoViewManager.instance();
        List<String> channelNames = new ArrayList<>();
        List<VideoChannelTable> videoChannelTableList = VideosChannelTableManager.loadVideosChannelsMine();
        List<Fragment> mNewsFragmentList = new ArrayList<>();
        for (int i = 0; i < videoChannelTableList.size(); i++) {
            channelNames.add(videoChannelTableList.get(i).getChannelName());
            mNewsFragmentList.add(createListFragments(videoChannelTableList.get(i)));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList, channelNames);
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        MyUtils.dynamicSetTabLayoutMode(tabs);
        setPageChangeListener();
    }

    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mVideoViewManager.stopPlayback();//切换时直接停止播放
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private MovieTypeFragment createListFragments(VideoChannelTable videoChannelTable) {
        MovieTypeFragment fragment = new MovieTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.VIDEO_TYPE, videoChannelTable.getChannelId());
        fragment.setArguments(bundle);
        return fragment;
    }


}
