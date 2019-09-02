package com.xinzy.java.wan.biz.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.api.WanApiModel;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.entity.WanList;
import com.xinzy.java.wan.widget.StatusView;
import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.util.Collections;
import com.xinzy.mvvm.lib.util.L;
import com.xinzy.mvvm.lib.view.widget.MultiAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static com.xinzy.java.wan.util.Macro.KEY_TITLE;
import static com.xinzy.java.wan.util.Macro.KEY_URL;
import static com.xinzy.java.wan.util.Macro.ROUTER_WEB;

public class WeixinTopicViewModel extends BaseViewModel<WanApiModel> {
    private static final String TEMP_HINT = "在%s公众号中搜索";

    public final ObservableBoolean isRefreshing = new ObservableBoolean();
    public final ObservableField<StatusView.Status> displayStatus = new ObservableField<>(StatusView.Status.Normal);
    public final ObservableField<String> searchHint = new ObservableField<>();

    public MultiAdapter topicAdapter = new MultiAdapter(new MultiAdapter.ItemBinding(this, R.layout.item_topic));

    private String searchKeyword;
    private Chapter mChapter;
    private int mPage;
    private int mMaxPage = Integer.MAX_VALUE;

    public WeixinTopicViewModel(@NonNull Application application, Chapter chapter) {
        super(application);
        this.mChapter = chapter;

        searchHint.set(String.format(TEMP_HINT, mChapter.getName()));
    }

    public void onItemTopicClick(Object object, int position) {
        Topic topic = (Topic) object;
        ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, topic.getTitle())
                .withString(KEY_URL, topic.getLink()).navigation();
    }

    @SuppressLint("CheckResult")
    private void load(int page, String keyword) {

        if (page >= mMaxPage) return;
        final boolean isFirstPage = (page == 0);
        if (isFirstPage) isRefreshing.set(true);

        mModel.topicByWeixin(page, mChapter.getId(), keyword).subscribe(
                result -> {
                    isRefreshing.set(false);
                    if (result.isSuccess()) {

                        WanList<Topic> wanList = result.getData();
                        mMaxPage = wanList.getPageCount();
                        List<Topic> topics = wanList.getDatas();

                        if (Collections.isNotEmpty(topics)) {
                            if (isFirstPage) topicAdapter.replace(topics);
                            else topicAdapter.addAll(topics);
                        } else {
                            if (isFirstPage) displayStatus.set(StatusView.Status.Empty);
                        }
                        mPage++;
                    } else {
                        if (isFirstPage) displayStatus.set(StatusView.Status.Error);
                    }
                }, e -> {
                    isRefreshing.set(false);
                    if (isFirstPage) displayStatus.set(StatusView.Status.Error);
                }
        );
    }

    public void onNextPage() {
        load(mPage, searchKeyword);
    }

    public void refresh() {
        load(0, searchKeyword);
    }

    public void onSearch(String keyword) {
        searchKeyword = keyword;
        load(0, keyword);
        L.d("search text: " + keyword);
    }

    @Override
    public void onCreate() {
        load(0, searchKeyword);
    }
}
