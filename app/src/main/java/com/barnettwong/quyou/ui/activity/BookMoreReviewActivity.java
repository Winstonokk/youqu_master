package com.barnettwong.quyou.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.barnettwong.quyou.mvp.contract.BookDetailContract;
import com.barnettwong.quyou.mvp.model.BookDetailModel;
import com.barnettwong.quyou.mvp.presenter.BookDetailPresenter;
import com.barnettwong.quyou.ui.adapter.BookReviewsAdapter;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wang on 2018/12/27 12:15
 * 更多评论
 */
public class BookMoreReviewActivity extends BaseActivity<BookDetailPresenter, BookDetailModel> implements BookDetailContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static int count = 20;
    private int page = 0;
    private static String bookId;
    private static String bookName;
    private boolean isLoadAll;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private BookReviewsData mReviews;
    private BookReviewsAdapter bookReviewsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more_review;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_back));
        bookName = getIntent().getStringExtra("bookName");
        bookId = getIntent().getStringExtra("bookId");
        toolbar.setTitle(bookName + getString(R.string.comment_of_book));

        mReviews = new BookReviewsData();
        bookReviewsAdapter = new BookReviewsAdapter(mReviews);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);
        mLayoutManager = new LinearLayoutManager(BookMoreReviewActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        mRecyclerView.setAdapter(bookReviewsAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == bookReviewsAdapter.getItemCount()) {
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();

        initListener();
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void returnBookReviewList(BookReviewsData response) {
        if (response != null) {
           updateView(response);
        }
    }

    public void updateView(BookReviewsData response) {
        if (page == 0) {
            mReviews.getReviews().clear();
        }
        mReviews.getReviews().addAll(response.getReviews());
        bookReviewsAdapter.notifyDataSetChanged();
        if (response.getReviews().size() < count) {
            isLoadAll = true;
        } else {
            page++;
            isLoadAll = false;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        mPresenter.startBookReviewsRequest(bookId, page * count, count, COMMENT_FIELDS);
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            mPresenter.startBookReviewsRequest(bookId, page * count, count, COMMENT_FIELDS);
        } else {
            showMessage(getString(R.string.no_more));
        }
    }

    @Override
    public void returnBookSeriesList(BookListData bookListData) {

    }

    @Override
    public void showLoading(String title) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showErrorTip(String msg) {

    }

    public void showMessage(String msg) {
        Snackbar.make(toolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

}
