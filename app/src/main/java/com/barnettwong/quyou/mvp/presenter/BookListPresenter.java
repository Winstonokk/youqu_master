package com.barnettwong.quyou.mvp.presenter;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.mvp.contract.BookListContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by wang on 2018/8/8.
 **/
public class BookListPresenter extends BookListContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        //监听返回顶部动作
        mRxManage.on(AppConstant.BOOK_LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
    }

    @Override
    public void startBookListRequest(String q, String tag, int start, int count, String fields) {
        mRxManage.add(mModel.getBookList(q,tag,start,count,fields).subscribe(new RxSubscriber<List<BookInfoBean>>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<BookInfoBean> bookInfoBeanList) {
                mView.returnBookListResult(bookInfoBeanList);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
