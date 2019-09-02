package com.xinzy.java.wan.biz.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.biz.main.model.KnowledgeModel;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.java.wan.widget.StatusView;
import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.util.Collections;

import java.util.List;

import static com.xinzy.java.wan.util.Macro.KEY_CHAPTER;
import static com.xinzy.java.wan.util.Macro.ROUTER_TOPICS;

public class KnowledgeViewModel extends BaseViewModel<KnowledgeModel> {

    public ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public ObservableField<StatusView.Status> displayStatus = new ObservableField<>(StatusView.Status.Normal);

    public ObservableField<List<Chapter>> chapterList = new ObservableField<>();

    public KnowledgeViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void start() {
        isRefreshing.set(true);
        mModel.chapters().subscribe(
                result -> {
                    isRefreshing.set(false);
                    if (result.isSuccess()) {
                        List<Chapter> chapters = result.getData();
                        if (Collections.isNotEmpty(chapters)) {
                            displayStatus.set(StatusView.Status.Normal);
                            chapterList.set(chapters);
                        } else {
                            displayStatus.set(StatusView.Status.Empty);
                        }
                    } else {
                        displayStatus.set(StatusView.Status.Error);
                    }
                },
                e -> {
                    displayStatus.set(StatusView.Status.Error);
                });
    }

    public void onItemChapterClick(Object object, int position) {
        Chapter chapter = (Chapter) object;
        ARouter.getInstance().build(ROUTER_TOPICS).withParcelable(KEY_CHAPTER, chapter).navigation();
    }

    @Override
    public void onCreate() {
        start();
    }
}
