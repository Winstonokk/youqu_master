package com.barnettwong.quyou.bean.book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2018/12/27 09:56
 */
public class BookReviewsData {
    private int count;
    private int start;
    private int total;
    private List<BookReviewBean> reviews;

    public BookReviewsData() {
        this.reviews = new ArrayList<>();
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReviewBean> reviews) {
        this.reviews = reviews;
    }
}
