package com.barnettwong.quyou.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.barnettwong.quyou.R;
import com.barnettwong.quyou.app.MyApplication;
import com.barnettwong.quyou.bean.girl.PhotoGirl;
import com.barnettwong.quyou.mvp.contract.PhotoListContract;
import com.barnettwong.quyou.mvp.model.PhotosListModel;
import com.barnettwong.quyou.mvp.presenter.PhotosListPresenter;
import com.barnettwong.quyou.ui.widght.RatioImageView;
import com.barnettwong.quyou.util.SpacesItemDecoration;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonwidget.LoadingTip;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wang on 2018/12/25 14:58
 * 潮流美女
 */
public class GirlFragment extends BaseFragment<PhotosListPresenter, PhotosListModel> implements PhotoListContract.View, OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.irc)
    IRecyclerView irc;
    @BindView(R.id.loadedTip)
    LoadingTip loadedTip;
    private CommonRecycleViewAdapter<PhotoGirl> adapter;
    private static int SIZE = 20;
    private int mStartPage = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        adapter = new CommonRecycleViewAdapter<PhotoGirl>(getContext(), R.layout.item_photo) {
            @Override
            public void convert(ViewHolderHelper helper, final PhotoGirl photoGirl) {
                ImageView imageView = helper.getView(R.id.iv_photo);
//                Glide.with(mContext).load(photoGirl.getUrl())
//                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
//                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
//                        .centerCrop().override(1090, 1090 * 3 / 4)
//                        .crossFade().into(imageView);

                //Picasso 会自动计算图片的宽高比
                Picasso.with(MyApplication.getAppContext())
                        .load(photoGirl.getUrl())
                        .placeholder(com.jaydenxiao.common.R.drawable.ic_image_loading)
                        .error(com.jaydenxiao.common.R.drawable.ic_empty_picture)
                        .into(imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        PhotosDetailActivity.startAction(mContext,photoGirl.getUrl());
                    }
                });
            }
        };
        irc.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        irc.addItemDecoration(decoration);
        //注册刷新加载监听
        irc.setOnLoadMoreListener(this);
        irc.setOnRefreshListener(this);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

    @Override
    public void returnPhotosListData(List<PhotoGirl> photoGirls) {
        if (photoGirls != null) {
            mStartPage += 1;
            if (adapter.getPageBean().isRefresh()) {
                irc.setRefreshing(false);
                adapter.replaceAll(photoGirls);
            } else {
                if (photoGirls.size() > 0) {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                    adapter.addAll(photoGirls);
                } else {
                    irc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            }
        }
    }

    @Override
    public void showLoading(String title) {
        if (adapter.getPageBean().isRefresh())
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        if (adapter.getPageBean().isRefresh()) {
            loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
            loadedTip.setTips(msg);
            irc.setRefreshing(false);
        } else {
            irc.setLoadMoreStatus(LoadMoreFooterView.Status.ERROR);
        }
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        mStartPage = 0;
        //发起请求
        irc.setRefreshing(true);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        //发起请求
        irc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getPhotosListDataRequest(SIZE, mStartPage);
    }
}
