package com.barnettwong.quyou.bean.book;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang on 2018/12/25 16:39
 */
public class BookListData implements Serializable {
    private int count;
    private int start;
    private int total;
    protected List<BookInfoBean> books;

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

    public List<BookInfoBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookInfoBean> books) {
        this.books = books;
    }
}
