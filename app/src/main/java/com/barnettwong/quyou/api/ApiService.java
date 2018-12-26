package com.barnettwong.quyou.api;

import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.girl.GirlData;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 数据请求服务基类
 */
public interface ApiService {

    @GET("book/search")
    Observable<BookListData> getBookLists(@QueryMap Map<String, String> map);//图书列表

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);//美女
}
