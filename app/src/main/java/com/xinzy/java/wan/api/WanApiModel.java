package com.xinzy.java.wan.api;

import com.xinzy.java.wan.entity.ApiResult;
import com.xinzy.java.wan.entity.Banner;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.entity.WanList;
import com.xinzy.mvvm.lib.base.BaseModel;
import com.xinzy.mvvm.lib.network.HttpServices;
import com.xinzy.mvvm.lib.provider.RxUtil;

import java.util.List;

import io.reactivex.Observable;

public class WanApiModel extends BaseModel {

    protected WanApi mApi;

    public WanApiModel() {
        mApi = HttpServices.getService(WanApi.class);
    }

    /**
     * 首页文章列表
     */
    public Observable<ApiResult<WanList<Topic>>> getHomeTopic(int page) {
        return mApi.homeArticles(page).compose(RxUtil.applyUi());
    }

    /**
     * 置顶文章
     */
    public Observable<ApiResult<List<Topic>>> topTopic() {
        return mApi.topTopic().compose(RxUtil.applyUi());
    }

    /**
     * 首页banner
     */
    public Observable<ApiResult<List<Banner>>> banner() {
        return mApi.banner().compose(RxUtil.applyUi());
    }

    /**
     * 知识体系分类
     */
    public Observable<ApiResult<List<Chapter>>> chapters() {
        return mApi.chapters().compose(RxUtil.applyUi());
    }

    /**
     * 根据chapter id 查找文章列表
     */
    public Observable<ApiResult<WanList<Topic>>> topicByChapterId(int page, int cid) {
        return mApi.topicByChapterId(page, cid).compose(RxUtil.applyUi());
    }

    /**
     * 微信公众号列表
     */
    public Observable<ApiResult<List<Chapter>>> weixin() {
        return mApi.weixin().compose(RxUtil.applyUi());
    }

    public Observable<ApiResult<WanList<Topic>>> topicByWeixin(int page, int cid, String keyword) {
        return mApi.topicByWeixin(page, cid, keyword).compose(RxUtil.applyUi());
    }
}
