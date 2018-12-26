package com.barnettwong.quyou.mvp.contract;

import com.barnettwong.quyou.bean.girl.PhotoGirl;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 * des:图片列表contract
 */
public interface PhotoListContract {
    interface Model extends BaseModel {
        //请求获取图片
        Observable <List<PhotoGirl>> getPhotosListData(int size, int page);
    }

    interface View extends BaseView {
        //返回获取的图片
        void returnPhotosListData(List<PhotoGirl> photoGirls);
    }
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起获取图片请求
        public abstract void getPhotosListDataRequest(int size, int page);
    }
}
