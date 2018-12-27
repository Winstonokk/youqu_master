package com.barnettwong.quyou.api;

import com.barnettwong.quyou.bean.book.BookListData;
import com.barnettwong.quyou.bean.book.BookReviewsData;
import com.barnettwong.quyou.bean.girl.GirlData;
import com.barnettwong.quyou.bean.video.VideoData;

import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 数据请求服务基类
 */
public interface ApiService {

    @GET("book/search")
    Observable<BookListData> getBookLists(@QueryMap Map<String, String> map);//图书列表

    @GET("book/{bookId}/reviews")
    Observable<BookReviewsData> getBookReviews(@Path("bookId") String bookId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);//评论

    @GET("book/series/{seriesId}/books")
    Observable<BookListData> getBookSeries(@Path("seriesId") String seriesId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);//推荐书籍

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);//美女

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);//视频
}
