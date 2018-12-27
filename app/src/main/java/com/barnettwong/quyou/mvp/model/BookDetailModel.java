package com.barnettwong.quyou.mvp.model;

import com.barnettwong.quyou.api.Api;
import com.barnettwong.quyou.api.HostType;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewBean;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.barnettwong.quyou.mvp.contract.BookDetailContract;
import com.barnettwong.quyou.mvp.contract.BookListContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class BookDetailModel implements BookDetailContract.Model{

    @Override
    public Observable<BookReviewsData> getBookReviewList(String bookId, int start, int count, String fields) {

        return Api.getDefault(HostType.NEWS_SERVER)
                .getBookReviews(bookId,start,count,fields)
                .map(resultBean -> resultBean)
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<BookListData> getBookSeriesList(String SeriesId, int start, int count, String fields) {

        return Api.getDefault(HostType.NEWS_SERVER)
                .getBookSeries(SeriesId,start,count,fields)
                .map(resultBean -> resultBean)
                .compose(RxSchedulers.io_main());
    }
}
