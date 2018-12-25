package com.barnettwong.quyou.mvp.model;

import com.barnettwong.quyou.api.Api;
import com.barnettwong.quyou.api.HostType;
import com.barnettwong.quyou.bean.book.BookInfoBean;
import com.barnettwong.quyou.mvp.contract.BookListContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by wang on 2018/8/8.
 **/
public class BookListModel implements BookListContract.Model{

    @Override
    public Observable<List<BookInfoBean>> getBookList(String q, String tag, int start, int count, String fields) {
        Map<String, String> map = new HashMap<>();
        if(null!=q){
            map.put("q",q);
        }
        map.put("tag",tag);
        map.put("start",start+"");
        map.put("count",count+"");
        map.put("fields",fields);

        return Api.getDefault(HostType.NEWS_SERVER)
                .getBookLists(map)
                .map(resultBean -> resultBean.getBooks())
                .compose(RxSchedulers.io_main());
    }
}
