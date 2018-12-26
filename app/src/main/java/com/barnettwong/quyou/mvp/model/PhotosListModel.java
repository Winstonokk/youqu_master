package com.barnettwong.quyou.mvp.model;

import com.barnettwong.quyou.api.Api;
import com.barnettwong.quyou.api.HostType;
import com.barnettwong.quyou.bean.girl.GirlData;
import com.barnettwong.quyou.bean.girl.PhotoGirl;
import com.barnettwong.quyou.mvp.contract.PhotoListContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by wang on 2018/8/8.
 * des:图片
 */
public class PhotosListModel implements PhotoListContract.Model{
    @Override
    public Observable<List<PhotoGirl>> getPhotosListData(int size, int page) {
        return Api.getDefault(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(Api.getCacheControl(),size, page)
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girlData) {
                        return girlData.getResults();
                    }
                })
                .compose(RxSchedulers.<List<PhotoGirl>>io_main());
    }
}
