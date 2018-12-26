package com.barnettwong.quyou.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.mvp.contract.BookListContract;
import com.barnettwong.quyou.mvp.model.BookListModel;
import com.barnettwong.quyou.mvp.presenter.BookListPresenter;
import com.barnettwong.quyou.ui.adapter.BookListAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 14:58
 * 图书
 */
public class BookTypeFragment extends BaseFragment<BookListPresenter, BookListModel> implements BookListContract.View, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    private BookListAdapter bookListAdapter;

    private String tag = "hot";
    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series,alt";
    private static int count = 20;
    private static int page = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_book_type;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            tag = getArguments().getString(AppConstant.BOOK_TYPE);
        }
        irc.setLayoutManager(new LinearLayoutManager(getContext()));
        bookListAdapter = new BookListAdapter(getContext(), R.layout.item_book_list);
        bookListAdapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(bookListAdapter);
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);

        //数据为空才重新发起请求
        if (bookListAdapter.getSize() <= 0) {
            //发起请求
            page = 0;
            mPresenter.startBookListRequest(null, tag, page, count, fields);
        }
    }

    @Override
    public void returnBookListResult(List<BookInfoBean> bookInfoBeans) {
        if (bookInfoBeans != null) {
            page += 1;
            if (bookListAdapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                bookListAdapter.replaceAll(bookInfoBeans);
            } else {
                if (bookInfoBeans.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    bookListAdapter.addAll(bookInfoBeans);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        bookListAdapter.getPageBean().setRefresh(true);
        page = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.startBookListRequest(null, tag, page, count, fields);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        bookListAdapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.startBookListRequest(null, tag, page, count, fields);
    }

    @Override
    public void showLoading(String title) {
        if (bookListAdapter.getPageBean().isRefresh()) {
            if (bookListAdapter.getSize() <= 0) {
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
        if (bookListAdapter.getPageBean().isRefresh()) {
            if (bookListAdapter.getSize() <= 0) {
                loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
                loadedTip.setTips(msg);
                irc.setRefreshing(false);
            }
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    /**
     * 返回顶部
     */
    @Override
    public void scrolltoTop() {
        irc.smoothScrollToPosition(0);
    }
}
