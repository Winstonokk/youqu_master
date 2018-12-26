package com.barnettwong.quyou.mvp.contract;

import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 * 接口调用参数 tag：标签，q：搜索关键词，fields：过滤词，count：一次返回数据数，
 *  page：当前已经加载的页数，PS:tag,q只存在其中一个，另一个置空
 **/
public interface BookListContract {
    interface Model extends BaseModel {
        Observable<List<BookInfoBean>> getBookList(String q, String tag, int start, int count, String fields);
    }

    interface View extends BaseView {
        void returnBookListResult(List<BookInfoBean> bookInfoBeanList);

        //返回顶部
        void scrolltoTop();

    }
    abstract static class Presenter extends BasePresenter<View, Model> {

        public abstract void startBookListRequest(String q, String tag, int start, int count, String fields);

    }
}
