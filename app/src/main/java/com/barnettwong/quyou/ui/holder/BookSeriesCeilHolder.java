package com.barnettwong.quyou.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.ui.activity.BookDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.jaydenxiao.common.baseapp.BaseApplication;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 */
public class BookSeriesCeilHolder {
    private View mContentView;
    private ImageView iv_book_img;
    private TextView tv_title;
    private AppCompatRatingBar ratingBar_hots;
    private TextView tv_hots_num;
    private BookInfoBean mBookInfoResponse;
    private Activity mContext;

    public BookSeriesCeilHolder(Activity mContext,BookInfoBean bookInfoResponse) {
        this.mContext=mContext;
        this.mBookInfoResponse = bookInfoResponse;
        initView();
        initEvent();
    }

    private void initView() {
        mContentView = LayoutInflater.from(BaseApplication.getAppContext()).inflate(R.layout.item_book_series_ceil, null, false);
        iv_book_img = (ImageView) mContentView.findViewById(R.id.iv_book_img);
        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        ratingBar_hots = (AppCompatRatingBar) mContentView.findViewById(R.id.ratingBar_hots);
        tv_hots_num = (TextView) mContentView.findViewById(R.id.tv_hots_num);
    }

    private void initEvent() {
        Glide.with(BaseApplication.getAppContext())
                .load(mBookInfoResponse.getImages().getLarge())
                .into(iv_book_img);
        tv_title.setText(mBookInfoResponse.getTitle());
        ratingBar_hots.setRating(Float.valueOf(mBookInfoResponse.getRating().getAverage()) / 2);
        tv_hots_num.setText(mBookInfoResponse.getRating().getAverage());
        mContentView.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putSerializable(BookInfoBean.serialVersionName, mBookInfoResponse);
            Bitmap bitmap;

            bitmap = ((GlideBitmapDrawable) (iv_book_img.getDrawable())).getBitmap();
            b.putParcelable("book_img", bitmap);
            Intent intent = new Intent(BaseApplication.getAppContext(), BookDetailActivity.class);
            intent.putExtras(b);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mContext == null) {
                    startActivity(intent);
                    return;
                }
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(mContext, iv_book_img, "book_img");
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        });
    }

    public View getContentView() {
        return mContentView;
    }
}
