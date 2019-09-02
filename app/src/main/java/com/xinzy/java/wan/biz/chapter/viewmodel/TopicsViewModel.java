package com.xinzy.java.wan.biz.chapter.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.chapter.model.TopicsModel;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.entity.WanList;
import com.xinzy.java.wan.widget.StatusView;
import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.util.Collections;
import com.xinzy.mvvm.lib.view.widget.MultiAdapter;

import java.util.List;

import static com.xinzy.java.wan.util.Macro.KEY_TITLE;
import static com.xinzy.java.wan.util.Macro.KEY_URL;
import static com.xinzy.java.wan.util.Macro.ROUTER_WEB;

public class TopicsViewModel extends BaseViewModel<TopicsModel> {

    public final ObservableBoolean isRefreshing = new ObservableBoolean();
    public final ObservableField<StatusView.Status> displayStatus = new ObservableField<>(StatusView.Status.Normal);

    public MultiAdapter topicAdapter = new MultiAdapter(new MultiAdapter.ItemBinding(this, R.layout.item_topic));

    private Chapter mChapter;
    private int mPage;
    private int mMaxPage = Integer.MAX_VALUE;

    private boolean isLoading;

    public TopicsViewModel(@NonNull Application application, Chapter chapter) {
        super(application);
        this.mChapter = chapter;
    }

    public void onItemTopicClick(Object object, int position) {
        Topic topic = (Topic) object;
        ARouter.getInstance().build(ROUTER_WEB).withString(KEY_TITLE, topic.getTitle())
                .withString(KEY_URL, topic.getLink()).navigation();
    }

    @SuppressLint("CheckResult")
    private void load(int page) {
        if (isLoading) return;
        if (page >= mMaxPage) return;

        final boolean isFirstPage = (page == 0);
        if (isFirstPage) isRefreshing.set(true);

        mModel.topicByChapterId(page, mChapter.getId()).subscribe(
                result -> {
                    isLoading = false;
                    isRefreshing.set(false);
                    if (result.isSuccess()) {

                        WanList<Topic> wanList = result.getData();
                        mMaxPage = wanList.getPageCount();
                        mPage = wanList.getPage();
                        List<Topic> topics = wanList.getDatas();

                        if (Collections.isNotEmpty(topics)) {
                            if (isFirstPage) topicAdapter.replace(topics);
                            else topicAdapter.addAll(topics);
                        } else {
                            if (isFirstPage) displayStatus.set(StatusView.Status.Empty);
                        }
                    } else {
                        if (isFirstPage) displayStatus.set(StatusView.Status.Error);
                    }
                }, e -> {
                    isLoading = false;
                    isRefreshing.set(false);
                    if (isFirstPage) displayStatus.set(StatusView.Status.Error);
                }
        );
    }

    public void onNextPage() {
        load(mPage);
    }

    public void refresh() {
        load(0);
    }

    @Override
    public void onCreate() {
        load(0);
    }
}
