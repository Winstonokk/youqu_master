package com.barnettwong.quyou.mvp.presenter;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.video.VideoData;
import com.barnettwong.quyou.mvp.contract.VideosListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

import rx.functions.Action1;

/**
 * des:
 */
public class VideoListPresenter extends VideosListContract.Presenter {

    /**
     * 获取视频列表请求
     * @param type
     * @param startPage
     */
    @Override
    public void getVideosListDataRequest(String type, int startPage) {
        mRxManage.add(mModel.getVideosListData(type,startPage).subscribe(new RxSubscriber<List<VideoData>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }
            @Override
            protected void _onNext(List<VideoData> videoDatas) {
                mView.returnVideosListData(videoDatas);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
