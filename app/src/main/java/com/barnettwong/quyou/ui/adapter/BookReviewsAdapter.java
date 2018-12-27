package com.barnettwong.quyou.ui.adapter;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barnettwong.quyou.R;
import com.barnettwong.quyou.bean.book.BookReviewBean;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jaydenxiao.common.baseapp.BaseApplication;

import java.util.List;


/**
 * 图书评论适配器
 */
public class BookReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private static final int AVATAR_SIZE_DP = 24;
    private final BookReviewsData reviewsListResponse;

    public BookReviewsAdapter(BookReviewsData responses) {
        this.reviewsListResponse = responses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_DEFAULT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_comment, parent, false);
            return new BookCommentHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (reviewsListResponse.getReviews() == null || reviewsListResponse.getReviews().isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BookCommentHolder) {
            List<BookReviewBean> reviews = reviewsListResponse.getReviews();
            Glide.with(BaseApplication.getAppContext())
                    .load(reviews.get(position).getAuthor().getAvatar())
                    .asBitmap()
                    .centerCrop()
                    .into(new BitmapImageViewTarget(((BookCommentHolder) holder).iv_avatar) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(BaseApplication.getAppContext().getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            ((BookCommentHolder) holder).iv_avatar.setImageDrawable(circularBitmapDrawable);
                        }
                    });
            ((BookCommentHolder) holder).tv_user_name.setText(reviews.get(position).getAuthor().getName());
            if (reviews.get(position).getRating() != null) {
                ((BookCommentHolder) holder).ratingBar_hots.setRating(Float.valueOf(reviews.get(position).getRating().getValue()));
            }
            ((BookCommentHolder) holder).tv_comment_content.setText(reviews.get(position).getSummary());
            ((BookCommentHolder) holder).tv_favorite_num.setText(reviews.get(position).getVotes() + "");
            ((BookCommentHolder) holder).tv_update_time.setText(reviews.get(position).getUpdated().split(" ")[0]);
        }
    }

    @Override
    public int getItemCount() {
        return reviewsListResponse.getReviews().size();
    }

    class BookCommentHolder extends RecyclerView.ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_user_name;
        private AppCompatRatingBar ratingBar_hots;
        private TextView tv_comment_content;
        private TextView tv_favorite_num;
        private TextView tv_update_time;

        public BookCommentHolder(View itemView) {
            super(itemView);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            ratingBar_hots = (AppCompatRatingBar) itemView.findViewById(R.id.ratingBar_hots);
            tv_comment_content = (TextView) itemView.findViewById(R.id.tv_comment_content);
            tv_favorite_num = (TextView) itemView.findViewById(R.id.tv_favorite_num);
            tv_update_time = (TextView) itemView.findViewById(R.id.tv_update_time);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder {

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
