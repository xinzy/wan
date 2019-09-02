package com.xinzy.java.wan.biz.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xinzy.java.wan.biz.main.model.WeixinModel;
import com.xinzy.java.wan.entity.ApiResult;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.base.BaseViewModel;

import java.util.List;

public class WeixinViewModel extends BaseViewModel<WeixinModel> {

    public MutableLiveData<List<Chapter>> weixinListEvent = new MutableLiveData<>();

    public WeixinViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        start();
    }

    @SuppressLint("CheckResult")
    private void start() {
        mModel.weixin().map(ApiResult::getData).subscribe(result -> weixinListEvent.postValue(result));
    }
}
