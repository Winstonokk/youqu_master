package com.barnettwong.quyou.mvp.contract;

import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 * 图书详情页
 **/
public interface BookDetailContract {
    interface Model extends BaseModel {
        /**
         * 获取图书评论
         */
        Observable<BookReviewsData> getBookReviewList(String bookId, int start, int count, String fields);

        /**
         * 获取推荐丛书
         */
        Observable<BookListData> getBookSeriesList(String SeriesId, int start, int count, String fields);

    }

    interface View extends BaseView {
        void returnBookReviewList(BookReviewsData bookReviewsData);

        void returnBookSeriesList(BookListData bookListData);

    }
    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void startBookReviewsRequest(String bookId, int start, int count, String fields);

        public abstract void startBookSeriesRequest(String SeriesId, int start, int count, String fields);

    }
}
