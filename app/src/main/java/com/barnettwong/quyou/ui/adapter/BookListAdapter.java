package com.barnettwong.quyou.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ImageView;

import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.barnettwong.quyou.R;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.ui.activity.BookDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ImageLoaderUtils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * Created by wang on 2018/12/25 16:45
 */
public class BookListAdapter extends CommonRecycleViewAdapter<BookInfoBean> {
    private Context mContext;

    public BookListAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mContext=context;
    }

    @Override
    public void convert(ViewHolderHelper helper, BookInfoBean bookInfo) {
        ImageLoaderUtils.display(mContext,helper.getView(R.id.iv_book_img),bookInfo.getImages().getLarge());
        helper.setText(R.id.tv_book_title,bookInfo.getTitle());
        helper.setText(R.id.tv_hots_num,bookInfo.getRating().getAverage());
        helper.setText(R.id.tv_book_info,bookInfo.getInfoString());
        helper.setText(R.id.tv_book_description,"\u3000" + bookInfo.getSummary());
        AppCompatRatingBar ratingBar_hots=helper.getView(R.id.ratingBar_hots);
        ratingBar_hots.setRating(Float.valueOf(bookInfo.getRating().getAverage()) / 2);
        helper.getView(R.id.item_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle b = new Bundle();
//                b.putSerializable(BookInfoBean.serialVersionName, bookInfo);
//                Bitmap bitmap;
//                ImageView iv_book_img=helper.getView(R.id.iv_book_img);
//                GlideBitmapDrawable imageDrawable = (GlideBitmapDrawable) iv_book_img.getDrawable();
//                if (imageDrawable != null) {
//                    bitmap = imageDrawable.getBitmap();
//                    b.putParcelable("book_img", bitmap);
//                }
//                startActivity(BookDetailActivity.class,b);
            }
        });
    }
}
