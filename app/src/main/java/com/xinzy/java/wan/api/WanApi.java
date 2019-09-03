package com.xinzy.java.wan.api;

import com.xinzy.java.wan.entity.ApiResult;
import com.xinzy.java.wan.entity.Banner;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.entity.WanList;
import com.xinzy.mvvm.lib.annotation.BaseUri;
import com.xinzy.mvvm.lib.annotation.HttpConfig;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.xinzy.java.wan.util.Macro.BASE_URL;

@HttpConfig(timeout = 5, unsafe = true)
@BaseUri(BASE_URL)
public interface WanApi {

    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json")
    Observable<ApiResult<WanList<Topic>>> homeArticles(@Path("page") int page);

    /**
     * 置顶文章
     */
    @GET("/article/top/json")
    Observable<ApiResult<List<Topic>>> topTopic();

    /**
     * 首页banner
     */
    @GET("/banner/json")
    Observable<ApiResult<List<Banner>>> banner();

    /**
     * 知识体系分类
     */
    @GET("/tree/json")
    Observable<ApiResult<List<Chapter>>> chapters();

    /** 知识体系下的文章列表 */
    @GET("/article/list/{page}/json")
    Observable<ApiResult<WanList<Topic>>> topicByChapterId(@Path("page") int page, @Query("cid") int cid);

    /** 微信公众号列表 */
    @GET("/wxarticle/chapters/json")
    Observable<ApiResult<List<Chapter>>> weixin();

    /** 知识体系下的文章列表 */
    @GET("/wxarticle/list/{cid}/{page}/json")
    Observable<ApiResult<WanList<Topic>>> topicByWeixin(@Path("page") int page, @Path("cid") int cid,
                                                        @Query("k") String keyword);

    /** 项目分类列表 */
    @GET("/project/tree/json")
    Observable<ApiResult<List<Chapter>>> projectChapters();

    /** 项目分类下的文章列表*/
    @GET("/project/list/{page}/json")
    Observable<ApiResult<WanList<Topic>>> topicByProject(@Path("page") int page, @Query("cid") int cid);
}
