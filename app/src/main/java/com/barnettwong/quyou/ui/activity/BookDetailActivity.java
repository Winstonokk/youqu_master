package com.barnettwong.quyou.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.AppConstant;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.barnettwong.quyou.mvp.contract.BookDetailContract;
import com.barnettwong.quyou.mvp.model.BookDetailModel;
import com.barnettwong.quyou.mvp.presenter.BookDetailPresenter;
import com.barnettwong.quyou.ui.adapter.BookDetailAdapter;
import com.barnettwong.quyou.util.Blur;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 17:26
 */
public class BookDetailActivity extends BaseActivity<BookDetailPresenter, BookDetailModel> implements BookDetailContract.View {
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private LinearLayoutManager mLayoutManager;
    private BookDetailAdapter mDetailAdapter;
    private ImageView iv_book_img;
    private ImageView iv_book_bg;

    private BookInfoBean mBookInfo;
    private BookReviewsData mReviewsListResponse;
    private BookListData mSeriesListResponse;

    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static final String SERIES_FIELDS = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series";
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 6;
    private static final int PAGE = 0;

    /**
     * 入口
     *
     * @param mContext
     */
    public static void startAction(Context mContext, View view, Bundle bundle) {
        Intent intent = new Intent(mContext, BookDetailActivity.class);
        intent.putExtras(bundle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) mContext,view, "book_img");
            mContext.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_back));

        mReviewsListResponse = new BookReviewsData();
        mSeriesListResponse = new BookListData();
        initIntentData();
        mLayoutManager = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDetailAdapter = new BookDetailAdapter(BookDetailActivity.this,mBookInfo, mReviewsListResponse, mSeriesListResponse);
        mRecyclerView.setAdapter(mDetailAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //头部图片
        iv_book_img = (ImageView) findViewById(R.id.iv_book_img);
        iv_book_bg = (ImageView) findViewById(R.id.iv_book_bg);
        mCollapsingLayout.setTitle(mBookInfo.getTitle());

        Bitmap book_img = getIntent().getParcelableExtra("book_img");
        if (book_img != null) {
            iv_book_img.setImageBitmap(book_img);
            iv_book_bg.setImageBitmap(Blur.apply(book_img));
            iv_book_bg.setAlpha(0.9f);
        } else {
            Glide.with(this)
                    .load(mBookInfo.getImages().getLarge())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            iv_book_img.setImageBitmap(resource);
                            iv_book_bg.setImageBitmap(Blur.apply(resource));
                            iv_book_bg.setAlpha(0.9f);
                        }
                    });
        }
        mPresenter.startBookReviewsRequest(mBookInfo.getId(), PAGE * REVIEWS_COUNT, REVIEWS_COUNT, COMMENT_FIELDS);

        initListener();
    }

    private void initIntentData() {
        Intent intent=getIntent();
        if(null!=intent){
            Bundle bundle=intent.getExtras();
            if(null!=bundle){
                if(bundle.containsKey(BookInfoBean.serialVersionName)){
                    mBookInfo= (BookInfoBean) bundle.getSerializable(BookInfoBean.serialVersionName);
                }
            }
        }
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
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void returnBookReviewList(BookReviewsData response) {
        mReviewsListResponse.setTotal(response.getTotal());
        mReviewsListResponse.getReviews().addAll(response.getReviews());
        mDetailAdapter.notifyDataSetChanged();
        if (mBookInfo.getSeries() != null) {
            mPresenter.startBookSeriesRequest(mBookInfo.getSeries().getId(), PAGE * SERIES_COUNT, 6, SERIES_FIELDS);
        }
    }

    @Override
    public void returnBookSeriesList(BookListData response) {
        mSeriesListResponse.setTotal(response.getTotal());
        mSeriesListResponse.getBooks().addAll(response.getBooks());
        mDetailAdapter.notifyDataSetChanged();
    }
}
