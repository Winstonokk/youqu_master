package com.barnettwong.quyou.mvp.presenter;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewBean;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.barnettwong.quyou.mvp.contract.BookDetailContract;
import com.barnettwong.quyou.mvp.contract.BookListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by wang on 2018/8/8.
 **/
public class BookDetailPresenter extends BookDetailContract.Presenter {

    @Override
    public void startBookReviewsRequest(String bookId, int start, int count, String fields) {
        mRxManage.add(mModel.getBookReviewList(bookId,start,count,fields).subscribe(new RxSubscriber<BookReviewsData>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BookReviewsData bookReviewsData) {
                mView.returnBookReviewList(bookReviewsData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void startBookSeriesRequest(String SeriesId, int start, int count, String fields) {
        mRxManage.add(mModel.getBookSeriesList(SeriesId,start,count,fields).subscribe(new RxSubscriber<BookListData>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BookListData bookListData) {
                mView.returnBookSeriesList(bookListData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
