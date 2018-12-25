package com.barnettwong.quyou.api;

import com.barnettwong.quyou.bean.book.BookListData;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 */
public interface ApiService {

    @GET("book/search")
    Observable<BookListData> getBookLists(@QueryMap Map<String, String> map);//图书列表
}
