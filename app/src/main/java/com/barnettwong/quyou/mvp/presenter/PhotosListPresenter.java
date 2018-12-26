package com.barnettwong.quyou.mvp.presenter;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.bean.girl.PhotoGirl;
import com.barnettwong.quyou.mvp.contract.PhotoListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by wang on 2018/8/8.
 * des:图片列表
 */
public class PhotosListPresenter extends PhotoListContract.Presenter{
    @Override
    public void getPhotosListDataRequest(int size, int page) {
             mRxManage.add(mModel.getPhotosListData(size,page).subscribe(new RxSubscriber<List<PhotoGirl>>(mContext,false) {
                 @Override
                 public void onStart() {
                     super.onStart();
                     mView.showLoading(mContext.getString(R.string.loading));
                 }
                 @Override
                 protected void _onNext(List<PhotoGirl> photoGirls) {
                     mView.returnPhotosListData(photoGirls);
                     mView.stopLoading();
                 }

                 @Override
                 protected void _onError(String message) {
                     mView.showErrorTip(message);
                 }
             }));
    }
}
