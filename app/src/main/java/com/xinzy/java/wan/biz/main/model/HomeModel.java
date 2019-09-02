package com.xinzy.java.wan.biz.main.model;

import android.annotation.SuppressLint;

import com.xinzy.java.wan.api.WanApiModel;
import com.xinzy.java.wan.entity.ApiResult;
import com.xinzy.java.wan.entity.Banner;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.entity.WanList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class HomeModel extends WanApiModel {

    @SuppressLint("CheckResult")
    public Observable<List<Object>> homePage() {
        Observable<ApiResult<List<Banner>>> banner = banner();
        Observable<ApiResult<List<Topic>>> hot = topTopic();
        Observable<ApiResult<WanList<Topic>>> topics = getHomeTopic(0);

        return Observable.zip(banner, hot, topics, (listApiResult, listApiResult2, wanListApiResult) -> {
            List<Object> list = new ArrayList<>();
            list.add(listApiResult.getData());
            list.addAll(listApiResult2.getData());
            list.addAll(wanListApiResult.getData().getDatas());

            return list;
        });
    }

}
