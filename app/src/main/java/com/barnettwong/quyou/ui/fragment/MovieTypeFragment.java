package com.barnettwong.quyou.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.video.VideoData;
import com.barnettwong.quyou.mvp.contract.VideosListContract;
import com.barnettwong.quyou.mvp.model.VideosListModel;
import com.barnettwong.quyou.mvp.presenter.VideoListPresenter;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.List;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by wang on 2018/12/25 14:58
 * 分类视频列表
 */
public class MovieTypeFragment extends BaseFragment<VideoListPresenter, VideosListModel> implements VideosListContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    private CommonRecycleViewAdapter<VideoData> videoListAdapter;

    private int mStartPage=0;
    private String mVideoType;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_movie_type;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            mVideoType = getArguments().getString(AppConstant.VIDEO_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        videoListAdapter =new CommonRecycleViewAdapter<VideoData>(getContext(),R.layout.item_video_list) {
            @Override
            public void convert(ViewHolderHelper helper, VideoData videoData) {
                helper.setImageRoundUrl(R.id.iv_logo,videoData.getTopicImg());
                helper.setText(R.id.tv_from,videoData.getTopicName());
                helper.setText(R.id.tv_play_time,String.format(getResources().getString(R.string.video_play_times), String.valueOf(videoData.getPlayCount())));
                IjkVideoView gsyVideoPlayer = helper.getView(R.id.videoplayer);
                initGsyVideoPlayer(gsyVideoPlayer, videoData);
            }
        };
        irc.setAdapter(videoListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        //视频监听
        irc.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                IjkVideoView ijkVideoView = view.findViewById(R.id.videoplayer);
                if (ijkVideoView != null && !ijkVideoView.isFullScreen()) {
                    ijkVideoView.stopPlayback();
                }
            }
        });
        //数据为空才重新发起请求
        if(videoListAdapter.getSize()<=0) {
            //发起请求
            mStartPage=0;
            mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
        }
    }

    private void initGsyVideoPlayer(IjkVideoView ijkVideoView, VideoData videoBean) {
        StandardVideoController controller = new StandardVideoController(getContext());
        controller.showTitle();
        PlayerConfig mPlayerConfig = new PlayerConfig.Builder()
                    .enableCache()
                .enableMediaCodec()
//                .autoRotate()
                .addToPlayerManager()//required
//                        .savingProgress()
                .setCustomMediaPlayer(new IjkPlayer(getContext()){
                    @Override
                    public void setOptions() {
                        super.setOptions();
                        //支持concat
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "safe", 0);
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist",
                                "rtmp,concat,ffconcat,file,subfile,http,https,tls,rtp,tcp,udp,crypto,rtsp");
                        //使用tcp方式拉取rtsp流，默认是通过udp方式
                        mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
                    }
                })
                .build();
        ImageLoaderUtils.display(getContext(),controller.getThumb(),videoBean.getCover());
        ijkVideoView.setPlayerConfig(mPlayerConfig);
        ijkVideoView.setUrl(videoBean.getMp4_url());
        ijkVideoView.setTitle(videoBean.getTitle());
        ijkVideoView.setVideoController(controller);
    }


    @Override
    public void returnVideosListData(List<VideoData> videoDatas) {
        if (videoDatas != null) {
            mStartPage += 1;
            if (videoListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                videoListAdapter.replaceAll(videoDatas);
            } else {
                if (videoDatas.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    videoListAdapter.addAll(videoDatas);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        videoListAdapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        videoListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getVideosListDataRequest(mVideoType, mStartPage);
    }

    @Override
    public void showLoading(String title) {
        if( videoListAdapter.getPageBean().isRefresh()) {
            if(videoListAdapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
            }
        }
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if( videoListAdapter.getPageBean().isRefresh()) {
            if(videoListAdapter.getSize()<=0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
                irc.setRefreshing(false);
            }
        }else{
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }
}
