package com.xinzy.java.wan.biz.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.model.HomeModel;
import com.xinzy.java.wan.entity.Banner;
import com.xinzy.java.wan.entity.Topic;
import com.xinzy.java.wan.util.Macro;
import com.xinzy.java.wan.widget.StatusView;
import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.util.Collections;
import com.xinzy.mvvm.lib.util.L;
import com.xinzy.mvvm.lib.view.widget.BannerView;
import com.xinzy.mvvm.lib.view.widget.MultiAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel<HomeModel> {

    public MultiAdapter topicAdapter = new MultiAdapter(new HomeItemBinding(this));

    public ObservableField<StatusView.Status> displayStatus = new ObservableField<>(StatusView.Status.Normal);
    public ObservableBoolean isShowRefreshing = new ObservableBoolean(false);

    private int mPage;
    private int mMaxPage = Integer.MAX_VALUE;
    private boolean isLoading;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        start();
    }

    @SuppressLint("CheckResult")
    public void start() {
        isLoading = true;
        isShowRefreshing.set(true);
        mModel.homePage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    mPage = 1;
                    isLoading = false;
                    isShowRefreshing.set(false);
                    if (Collections.isEmpty(result)) {
                        displayStatus.set(StatusView.Status.Empty);
                    } else {
                        displayStatus.set(StatusView.Status.Normal);
                        topicAdapter.replace(result);
                    }
                }, e -> {
                    isLoading = false;
                    isShowRefreshing.set(false);
                    displayStatus.set(StatusView.Status.Error);
                });
    }

    /** 加载下一页数据 */
    @SuppressLint("CheckResult")
    public void onNextPage() {
        if (mPage >= mMaxPage) return;
        if (isLoading) return;
        isLoading = true;

        mModel.getHomeTopic(mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(results -> {
                    mPage ++;
                    mMaxPage = results.getData().getPageCount();
                    isLoading = false;
                    List<Topic> topics = results.getData().getDatas();
                    topicAdapter.addAll(topics);
                }, e -> {
                    isLoading = false;
                });
    }

    public void onBannerItemClick(BannerView banner, Object item, int position) {
        L.d("banner item click");
        Banner b = (Banner) item;
        ARouter.getInstance().build(Macro.ROUTER_WEB).withString(Macro.KEY_TITLE, b.getTitle())
                .withString(Macro.KEY_URL, b.getUrl()).navigation();
    }

    public void onItemTopicClick(Object object, int position) {
        L.d("topic click");
        Topic topic = (Topic) object;
        ARouter.getInstance().build(Macro.ROUTER_WEB).withString(Macro.KEY_TITLE, topic.getTitle())
                .withString(Macro.KEY_URL, topic.getLink()).navigation();
    }

    class HomeItemBinding extends MultiAdapter.ItemBinding {
        public HomeItemBinding(BaseViewModel viewModel) {
            super(viewModel);
        }

        @Override
        public int getItemViewType(Object object) {
            if (object instanceof List) {
                return R.layout.item_banner;
            } else {
                return R.layout.item_topic;
            }
        }
    }
}
